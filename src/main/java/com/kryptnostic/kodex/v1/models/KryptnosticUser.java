package com.kryptnostic.kodex.v1.models;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.FAMILY_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GIVEN_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.ID_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.PASSWORD_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.principal.GroupKey;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * Model for a Heracles user.
 *
 * @author Nick Hewitt
 *
 */
public final class KryptnosticUser implements User, Serializable {
    private static final long         serialVersionUID = 3581755283203968675L;
    private final UUID                id;
    private final String              realm;
    private final String              username;
    private final String              email;
    private final String              givenName;
    private final String              familyName;
    private final String              password;
    private final byte[]              certificate;
    private final Set<GroupKey>       groups           = Sets.newConcurrentHashSet();
    private final Map<String, String> attributes       = Maps.newConcurrentMap();

    @JsonCreator
    public KryptnosticUser(
            @JsonProperty( ID_FIELD ) UUID id,
            @JsonProperty( REALM_FIELD ) String realm,
            @JsonProperty( NAME_FIELD ) String username,
            @JsonProperty( GIVEN_NAME_FIELD ) String givenName,
            @JsonProperty( FAMILY_NAME_FIELD ) String familyName,
            @JsonProperty( EMAIL_FIELD ) String email,
            @JsonProperty( PASSWORD_FIELD ) String password,
            @JsonProperty( CERTIFICATE_PROPERTY ) Optional<byte[]> certificate,
            @JsonProperty( GROUPS_PROPERTY ) Set<GroupKey> groups,
            @JsonProperty( ATTRIBUTES_FIELD ) Map<String, String> attributes ) {
        this.id = id;
        this.realm = realm;
        this.username = username;
        this.groups.addAll( groups );
        this.attributes.putAll( attributes );
        this.password = password;
        this.certificate = certificate.or( new byte[ 0 ] );
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    private KryptnosticUser( HeraclesUserBuilder builder ) {
        this.id = builder.id;
        this.realm = builder.realm;
        this.username = builder.username;
        this.password = builder.password;
        this.certificate = builder.certificate;
        this.givenName = builder.givenName;
        this.familyName = builder.familyName;
        this.email = builder.email;
        this.groups.addAll( builder.groups );
        this.attributes.putAll( builder.attributes );
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonProperty( EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @Override
    @JsonProperty( GIVEN_NAME_FIELD )
    public String getGivenName() {
        return givenName;
    }

    @Override
    @JsonProperty( FAMILY_NAME_FIELD )
    public String getFamilyName() {
        return familyName;
    }

    @Override
    @JsonProperty( PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @Override
    @JsonProperty( CERTIFICATE_PROPERTY )
    public byte[] getCertificate() {
        return certificate;
    }

    @Override
    @JsonProperty( ATTRIBUTES_FIELD )
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    @JsonProperty( GROUPS_PROPERTY )
    public Set<GroupKey> getGroups() {
        return groups;
    }

    @Override
    @JsonProperty( NAME_FIELD )
    public String getName() {
        return this.username;
    }

    @Override
    @JsonProperty( REALM_FIELD )
    public String getRealm() {
        return this.realm;
    }

    @Override
    public String getAttribute( String key ) {
        return attributes.get( key );
    }

    @Override
    public String toString() {
        return "HeraclesUser [password=" + password + ", certificate=" + Arrays.toString( certificate ) + ", groups="
                + groups + ", attributes=" + attributes + "]";
    }

    public static class HeraclesUserBuilder {
        public UUID                id;
        public String              realm;
        public String              username;
        public String              email;
        public String              givenName;
        public String              familyName;
        public String              password;
        public BlockCiphertext     encryptedSalt = null;
        public byte[]              certificate;
        public Set<GroupKey>       groups;
        public Map<String, String> attributes;

        // TODO add created and update dates

        public HeraclesUserBuilder( String realm, String username ) {
            this.realm = realm;
            this.username = username;
            this.groups = Sets.newConcurrentHashSet();
            this.attributes = Maps.newConcurrentMap();
        }

        public HeraclesUserBuilder withPassword( String password ) {
            this.password = password;
            return this;
        }

        public HeraclesUserBuilder withEmptyPassword() {
            this.password = new String();
            return this;
        }

        public HeraclesUserBuilder withCertificate( byte[] certificate ) {
            this.certificate = certificate;
            return this;
        }

        public HeraclesUserBuilder withEmptyCertificate() {
            this.certificate = new byte[ 0 ];
            return this;
        }

        public HeraclesUserBuilder withEmail( String email ) {
            this.email = email;
            return this;
        }

        public HeraclesUserBuilder withGivenName( String givenName ) {
            this.givenName = givenName;
            return this;
        }

        public HeraclesUserBuilder withFamilyName( String familyName ) {
            this.familyName = familyName;
            return this;
        }

        public HeraclesUserBuilder withId( UUID id ) {
            this.id = id;
            return this;
        }

        public HeraclesUserBuilder asUser() {
            addGroup( SecurityGroups.USER );
            return this;
        }

        public HeraclesUserBuilder asDeveloper() {
            addGroup( SecurityGroups.DEVELOPER );
            return this;
        }

        public HeraclesUserBuilder asAdmin() {
            addGroup( SecurityGroups.ADMIN );
            return this;
        }

        public HeraclesUserBuilder asRegistrar() {
            addGroup( SecurityGroups.REGISTRAR );
            return this;
        }

        public HeraclesUserBuilder withGroups( Set<GroupKey> groups ) {
            this.groups.addAll( groups );
            return this;
        }

        private void addGroup( String groupName ) {
            this.groups.add( new GroupKey( this.realm, groupName ) );
        }

        public HeraclesUserBuilder withAttributes( Map<String, String> attributes ) {
            this.attributes.putAll( attributes );
            return this;
        }

        public KryptnosticUser build() {
            Preconditions.checkNotNull( this.realm );
            Preconditions.checkNotNull( this.username );
            Preconditions.checkNotNull( this.password );
            Preconditions.checkState( !this.groups.isEmpty(), "User must be assigned to at least one group." );
            return new KryptnosticUser( this );
        }
    }

}
