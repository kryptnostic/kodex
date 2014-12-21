package com.kryptnostic.heracles.principals.v1.requests;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.PASSWORD_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;

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
import com.kryptnostic.heracles.principals.v1.GroupKey;
import com.kryptnostic.heracles.registration.v1.requests.RegistrationRequest;
import com.kryptnostic.heracles.registration.v1.requests.RequestStatus;
import com.kryptnostic.kodex.v1.constants.Names;

public class UserRegistrationRequest implements RegistrationRequest,Serializable {
    private static final long serialVersionUID = -3691716959430804922L;
    private String                               name;
    private String                               realm;
    private Optional<String>                     email;
    private Set<GroupKey>                        groups;
    private Map<String, String> attributes;
    private byte[]                               password;
    private byte[]                               certificate;
    private RequestStatus                        status;
    private Optional<String>                     reason;

    public UserRegistrationRequest(String name) {
        this( name , Sets.<GroupKey>newHashSet() , Maps.<String,String>newHashMap() );
    }

    public UserRegistrationRequest(String name, Set<GroupKey> groups, Map<String, String> attributes) {
        Preconditions.checkNotNull( groups , "Groups cannot be null." );
        Preconditions.checkNotNull( attributes , "Attributes cannot be null." );
        this.name = name;
        this.groups = groups;
        this.attributes = Maps.newHashMap();
        this.attributes.putAll( attributes );
        this.password = new byte[0];
        this.certificate = new byte[0];
        this.reason = Optional.absent();
        this.status = RequestStatus.OPEN;
        this.email = Optional.absent();
    }

    public UserRegistrationRequest withName(String name) {
        this.name = name;
        return this;
    }

    public UserRegistrationRequest inRealm(String realm) {
        this.realm = realm;
        return this;
    }

    public UserRegistrationRequest withEmail(@Nullable String email) {
        this.email = Optional.fromNullable( email );
        return this;
    }
    
    public UserRegistrationRequest withReason(@Nullable String reason) {
        this.reason = Optional.fromNullable( reason );
        return this;
    }

    public UserRegistrationRequest memberOf(GroupKey group) {
        this.groups.add( group );
        return this;
    }

    public UserRegistrationRequest memberOf(Set<GroupKey> groups ) {
        this.groups.addAll( groups );
        return this;
    }

    public UserRegistrationRequest withStatus( RequestStatus status ) {
        this.status = Preconditions.checkNotNull( status , "Request status cannot be null." );
        return this;
    }
    
    public UserRegistrationRequest withStatus( @Nullable String reason ) {
        this.reason = Optional.fromNullable( reason );
        return this;
    }
    
    public UserRegistrationRequest withPassword(byte[] password) {
        this.password = password;
        return this;
    }

    public UserRegistrationRequest withCertificate(byte[] certificate) {
        this.certificate = certificate;
        return this;
    }

    public UserRegistrationRequest withCertificate(Certificate certificate) throws CertificateEncodingException {
        return withCertificate( certificate.getEncoded() );
    }

    public UserRegistrationRequest withAttributes( Map<String, String> attributes ) {
        this.attributes.putAll( attributes );
        return this;
    }

    public UserRegistrationRequest addAttribute(String attribute, String value) {
        attributes.put(  attribute , value );
        return this;
    }

    @JsonIgnore
    public String getId() {
        return realm + "." + name;
    }

    @JsonProperty(NAME_FIELD)
    public String getName() {
        return name;
    }

    @JsonProperty(REALM_FIELD)
    public String getRealm() {
        return realm;
    }

    @JsonProperty(EMAIL_FIELD)
    public String getEmail() {
        return email.or( "" );
    }

    @JsonProperty(PASSWORD_FIELD)
    public byte[] getPassword() {
        return password;
    }

    @JsonProperty(CERTIFICATE_PROPERTY)
    public byte[] getCertificate() {
        return certificate;
    }

    @JsonProperty(GROUPS_PROPERTY)
    public Set<GroupKey> getGroups() {
        return groups;
    }

    @JsonProperty(ATTRIBUTES_FIELD)
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @JsonProperty(Names.STATUS_FIELD)
    @Override
    public RequestStatus getStatus() {
        return status;
    }
    
    @Nullable
    @JsonProperty(Names.REASON_FIELD)
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
    
    @JsonCreator
    public static UserRegistrationRequest createRequest(
            @JsonProperty(NAME_FIELD) String name,
            @JsonProperty(REALM_FIELD) String realm,
            @JsonProperty(EMAIL_FIELD) Optional<String> email,
            @JsonProperty(PASSWORD_FIELD) Optional<byte[]> password, 
            @JsonProperty(CERTIFICATE_PROPERTY) Optional<byte[]> certificate,
            @JsonProperty(GROUPS_PROPERTY) Set<GroupKey> groups,
            @JsonProperty(ATTRIBUTES_FIELD) Map<String, String> attributes,
            @JsonProperty(Names.STATUS_FIELD) Optional<RequestStatus> status,
            @JsonProperty(Names.REASON_FIELD) Optional<String> reason
            ) {
        
        return newRequest( name )
                .inRealm( realm )
                .memberOf( groups )
                .withPassword( password.or( new byte[0] ) )
                .withCertificate( certificate.or( new byte[0] ) )
                .withAttributes( attributes )
                .withStatus( status.or( RequestStatus.OPEN ) )
                .withReason( reason.orNull() )
                .withEmail( email.orNull() );
    }

    public static UserRegistrationRequest newRequest(String name) {
        return new UserRegistrationRequest( name );
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest [name=" + name + ", realm=" + realm + ",email=" + email + ", groups=" + groups + ", attributes="
                + attributes + ", password=" + Arrays.toString( password ) + ", certificate="
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
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + Arrays.hashCode( password );
        result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
        result = prime * result + ( ( reason == null ) ? 0 : reason.hashCode() );
        result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!( obj instanceof UserRegistrationRequest )) {
            return false;
        }
        UserRegistrationRequest other = (UserRegistrationRequest) obj;
        if (attributes == null) {
            if (other.attributes != null) {
                return false;
            }
        } else if (!attributes.equals( other.attributes )) {
            return false;
        }
        if (!Arrays.equals( certificate , other.certificate )) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals( other.email )) {
            return false;
        }
        if (groups == null) {
            if (other.groups != null) {
                return false;
            }
        } else if (!groups.equals( other.groups )) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals( other.name )) {
            return false;
        }
        if (!Arrays.equals( password , other.password )) {
            return false;
        }
        if (realm == null) {
            if (other.realm != null) {
                return false;
            }
        } else if (!realm.equals( other.realm )) {
            return false;
        }
        if (reason == null) {
            if (other.reason != null) {
                return false;
            }
        } else if (!reason.equals( other.reason )) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
    }
}
