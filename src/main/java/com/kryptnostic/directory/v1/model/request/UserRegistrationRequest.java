package com.kryptnostic.directory.v1.model.request;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.FAMILY_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GIVEN_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.PASSWORD_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REASON_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.STATUS_FIELD;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.model.AbstractKey;
import com.kryptnostic.directory.v1.model.RegistrationRequest;
import com.kryptnostic.directory.v1.model.RequestStatus;
import com.kryptnostic.directory.v1.principal.GroupKey;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class UserRegistrationRequest implements RegistrationRequest, Serializable {
    private static final long   serialVersionUID = -3691716959430804922L;
    private String              realm;
    private String              password;
    private byte[]              certificate;
    private String              email;
    private Optional<String>    givenName;
    private Optional<String>    familyName;
    private Set<GroupKey>       groups;
    private Map<String, String> attributes;
    private RequestStatus       status;
    private Optional<String>    reason;

    public UserRegistrationRequest( String email ) {
        this( email, Sets.<GroupKey> newHashSet(), Maps.<String, String> newHashMap() );
    }

    public UserRegistrationRequest( String email, Set<GroupKey> groups, Map<String, String> attributes ) {
        Preconditions.checkNotNull( groups, "Groups cannot be null." );
        Preconditions.checkNotNull( attributes, "Attributes cannot be null." );
        Preconditions.checkNotNull( email, "Email cannot be null." );
        this.email = email;
        this.groups = groups;
        this.attributes = Maps.newHashMap();
        this.attributes.putAll( attributes );
        this.password = "";
        this.certificate = new byte[ 0 ];
        this.status = RequestStatus.OPEN;
        this.reason = Optional.absent();
        this.givenName = Optional.absent();
        this.familyName = Optional.absent();
    }

    @JsonCreator
    public static UserRegistrationRequest createRequest(
            @JsonProperty( REALM_FIELD ) String realm,
            @JsonProperty( EMAIL_FIELD ) String email,
            @JsonProperty( GIVEN_NAME_FIELD ) Optional<String> givenName,
            @JsonProperty( FAMILY_NAME_FIELD ) Optional<String> familyName,
            @JsonProperty( PASSWORD_FIELD ) String password,
            @JsonProperty( CERTIFICATE_PROPERTY ) Optional<byte[]> certificate,
            @JsonProperty( GROUPS_PROPERTY ) Set<GroupKey> groups,
            @JsonProperty( ATTRIBUTES_FIELD ) Map<String, String> attributes,
            @JsonProperty( STATUS_FIELD ) Optional<RequestStatus> status,
            @JsonProperty( REASON_FIELD ) Optional<String> reason ) {

        return newRequest( email ).inRealm( realm ).memberOf( groups ).withPassword( password )
                .withCertificate( certificate.or( new byte[ 0 ] ) ).withAttributes( attributes )
                .withStatus( status.or( RequestStatus.OPEN ) ).withReason( reason.orNull() )
                .withGivenName( givenName.orNull() ).withFamilyName( familyName.orNull() );
    }

    public static UserRegistrationRequest newRequest( String username ) {
        return new UserRegistrationRequest( username );
    }

    public UserRegistrationRequest inRealm( String realm ) {
        this.realm = realm;
        return this;
    }

    public UserRegistrationRequest withGivenName( @Nullable String givenName ) {
        this.givenName = Optional.fromNullable( givenName );
        return this;
    }

    public UserRegistrationRequest withFamilyName( @Nullable String familyName ) {
        this.familyName = Optional.fromNullable( familyName );
        return this;
    }

    public UserRegistrationRequest withReason( @Nullable String reason ) {
        this.reason = Optional.fromNullable( reason );
        return this;
    }

    public UserRegistrationRequest memberOf( GroupKey group ) {
        this.groups.add( group );
        return this;
    }

    public UserRegistrationRequest memberOf( Set<GroupKey> groups ) {
        this.groups.addAll( groups );
        return this;
    }

    public UserRegistrationRequest withStatus( RequestStatus status ) {
        this.status = Preconditions.checkNotNull( status, "Request status cannot be null." );
        return this;
    }

    public UserRegistrationRequest withStatus( @Nullable String reason ) {
        this.reason = Optional.fromNullable( reason );
        return this;
    }

    public UserRegistrationRequest withPassword( String password ) {
        this.password = password;
        return this;
    }

    public UserRegistrationRequest withCertificate( byte[] certificate ) {
        this.certificate = certificate;
        return this;
    }

    public UserRegistrationRequest withCertificate( Certificate certificate ) throws CertificateEncodingException {
        return withCertificate( certificate.getEncoded() );
    }

    public UserRegistrationRequest withAttributes( Map<String, String> attributes ) {
        this.attributes.putAll( attributes );
        return this;
    }

    public UserRegistrationRequest addAttribute( String attribute, String value ) {
        attributes.put( attribute, value );
        return this;
    }

    @JsonIgnore
    public String getId() {
        return realm + AbstractKey.DELIMITER + UserKey.normalizeName( email );
    }

    @JsonProperty( REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @JsonProperty( PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @JsonProperty( CERTIFICATE_PROPERTY )
    public byte[] getCertificate() {
        return certificate;
    }

    @JsonProperty( GROUPS_PROPERTY )
    public Set<GroupKey> getGroups() {
        return groups;
    }

    @JsonProperty( ATTRIBUTES_FIELD )
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @JsonProperty( STATUS_FIELD )
    @Override
    public RequestStatus getStatus() {
        return status;
    }

    @JsonProperty( EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( GIVEN_NAME_FIELD )
    public Optional<String> getGivenName() {
        return givenName;
    }

    @JsonProperty( FAMILY_NAME_FIELD )
    public Optional<String> getFamilyName() {
        return familyName;
    }

    @Nullable
    @JsonProperty( Names.REASON_FIELD )
    public Optional<String> getReason() {
        return reason;
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
    }

    public void deny() {
        this.status = RequestStatus.DENIED;
    }

    public void open() {
        this.status = RequestStatus.OPEN;
    }

    public void deny( Optional<String> reason ) {
        deny();
        this.reason = reason;
    }

    /**
     * For email we use case-insensitive comparison at ROOT locale.
     * 
     * @param otherEmail
     * @return
     */
    public boolean equalsNormalizedEmail( String otherEmail ) {
        return UserKey.normalizeName( email ).equals( UserKey.normalizeName( otherEmail ) );
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest [email=" + email + ", realm=" + realm + ",name=" + givenName + " " + familyName
                + ", groups=" + groups + ", attributes=" + attributes + ", password=" + password + ", certificate="
                + Arrays.toString( certificate ) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( attributes == null ) ? 0 : attributes.hashCode() );
        result = prime * result + Arrays.hashCode( certificate );
        result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
        result = prime * result + ( ( groups == null ) ? 0 : groups.hashCode() );
        result = prime * result + ( ( givenName == null ) ? 0 : givenName.hashCode() );
        result = prime * result + ( ( familyName == null ) ? 0 : familyName.hashCode() );
        result = prime * result + password.hashCode();
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        result = prime * result + ( ( reason == null ) ? 0 : reason.hashCode() );
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof UserRegistrationRequest ) ) {
            return false;
        }
        UserRegistrationRequest other = (UserRegistrationRequest) obj;
        if ( attributes == null ) {
            if ( other.attributes != null ) {
                return false;
            }
        } else if ( !attributes.equals( other.attributes ) ) {
            return false;
        }
        if ( !Arrays.equals( certificate, other.certificate ) ) {
            return false;
        }
        if ( email == null ) {
            if ( other.email != null ) {
                return false;
            }
        } else if ( !equalsNormalizedEmail( other.email ) ) {
            return false;
        }
        if ( groups == null ) {
            if ( other.groups != null ) {
                return false;
            }
        } else if ( !groups.equals( other.groups ) ) {
            return false;
        }
        if ( givenName == null ) {
            if ( other.givenName != null ) {
                return false;
            }
        } else if ( !givenName.equals( other.givenName ) ) {
            return false;
        }
        if ( familyName == null ) {
            if ( other.familyName != null ) {
                return false;
            }
        } else if ( !familyName.equals( other.familyName ) ) {
            return false;
        }
        if ( !password.equals( other.password ) ) {
            return false;
        }
        if ( realm == null ) {
            if ( other.realm != null ) {
                return false;
            }
        } else if ( !realm.equals( other.realm ) ) {
            return false;
        }
        if ( reason == null ) {
            if ( other.reason != null ) {
                return false;
            }
        } else if ( !reason.equals( other.reason ) ) {
            return false;
        }
        if ( status != other.status ) {
            return false;
        }
        return true;
    }

}
