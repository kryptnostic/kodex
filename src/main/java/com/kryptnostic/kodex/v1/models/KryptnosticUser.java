package com.kryptnostic.kodex.v1.models;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.DOMAIN_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.FAMILY_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GIVEN_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.ID_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.ROLES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.USERNAME_FIELD;

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
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * Model for a Heracles user.
 *
 * @author Nick Hewitt
 *
 */
public final class KryptnosticUser implements User, Serializable {
    private static final long    serialVersionUID = 3581755283203968675L;
    private final UUID           id;
    private final String         email;
    private final String         domain;
    private final String         username;
    private final byte[]         certificate;
    private final Set<UUID>      groups           = Sets.newHashSet();
    private final Set<String>    roles            = Sets.newHashSet();
    private final UserAttributes attributes       = new UserAttributes( Maps.<String, String> newConcurrentMap() );

    /**
     * Either the realm or domain must be specified. If both are specified the realm takes precedence until it is removed as a field.
     * 
     * @param id
     * @param realm
     * @param domain
     * @param username
     * @param email
     * @param certificate
     * @param groups
     * @param roles
     * @param attributes
     */
    @JsonCreator
    public KryptnosticUser(
            @JsonProperty( ID_FIELD ) UUID id,
            @JsonProperty( REALM_FIELD ) Optional<String> realm,
            @JsonProperty( DOMAIN_FIELD) Optional<String> domain,
            @JsonProperty( USERNAME_FIELD ) String username,
            @JsonProperty( EMAIL_FIELD ) String email,
            @JsonProperty( CERTIFICATE_FIELD ) Optional<byte[]> certificate,
            @JsonProperty( GROUPS_PROPERTY ) Set<UUID> groups,
            @JsonProperty( ROLES_FIELD ) Set<String> roles,
            @JsonProperty( ATTRIBUTES_FIELD ) UserAttributes attributes ) {
        this.id = id;
        this.domain = realm.or( domain.get() );
        this.username = username;
        this.groups.addAll( groups );
        this.attributes.putAll( attributes );
        this.roles.addAll( roles );
        this.certificate = certificate.or( new byte[ 0 ] );
        this.email = email;
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
    public Optional<String> getGivenName() {
        return Optional.fromNullable( attributes.get( GIVEN_NAME_FIELD ) );
    }

    @Override
    @JsonProperty( FAMILY_NAME_FIELD )
    public Optional<String> getFamilyName() {
        return Optional.fromNullable( attributes.get( FAMILY_NAME_FIELD ) );
    }

    @Override
    @JsonProperty( CERTIFICATE_FIELD )
    public byte[] getCertificate() {
        return certificate;
    }

    @Override
    @JsonProperty( ATTRIBUTES_FIELD )
    public UserAttributes getAttributes() {
        return attributes;
    }

    @Override
    @JsonProperty( GROUPS_PROPERTY )
    public Set<UUID> getGroups() {
        return groups;
    }

    @Override
    @JsonProperty( USERNAME_FIELD )
    public String getName() {
        return this.username;
    }

    @Override
    @JsonProperty( REALM_FIELD )
    public String getRealm() {
        return getDomain();
    }
    
    @Override
    @JsonProperty( DOMAIN_FIELD )
    public String getDomain() {
        return this.domain;
    } 

    @Override
    public Optional<String> getAttribute( String key ) {
        return Optional.fromNullable( attributes.get( key ) );
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "HeraclesUser [id=" + id + ",username=" + username + ", certificate=" + Arrays.toString( certificate )
                + ", groups=" + groups + ", attributes=" + attributes + "]";
    }

    public static class HeraclesUserBuilder {
        public UUID            id;
        public String          domain;
        public String          username;
        public String          email;
        public String          givenName;
        public String          familyName;
        public BlockCiphertext encryptedSalt = null;
        public byte[]          certificate;
        public Set<UUID>       groups;
        public Set<String>     roles;
        public UserAttributes  attributes;

        public HeraclesUserBuilder( String email ) {
            this.email = email;
            this.username = email;
            this.givenName = "";
            this.familyName = "";
            this.certificate = new byte[ 0 ];
            this.groups = Sets.newConcurrentHashSet();
            this.attributes = new UserAttributes( Maps.<String,String>newConcurrentMap() );
            this.roles = Sets.newConcurrentHashSet();
        }

        @Deprecated
        public HeraclesUserBuilder withRealm( String realm ) {
            this.domain = realm;
            return this;
        }
        
        public HeraclesUserBuilder withDomain( String domain ) {
            this.domain = domain;
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
            addRoles( SecurityRoles.USER );
            return this;
        }

        public HeraclesUserBuilder asDeveloper() {
            addRoles( SecurityRoles.DEVELOPER );
            return this;
        }

        public HeraclesUserBuilder asAdmin() {
            addRoles( SecurityRoles.ADMIN );
            return this;
        }

        public HeraclesUserBuilder asRegistrar() {
            addRoles( SecurityRoles.REGISTRAR );
            return this;
        }

        public HeraclesUserBuilder withGroups( Set<UUID> groups ) {
            Preconditions.checkNotNull( groups, "Groups cannot be null." );
            this.groups.addAll( groups );
            return this;
        }

        private void addRoles( String role ) {
            this.roles.add( role );
        }

        public HeraclesUserBuilder withAttributes( Map<String, String> attributes ) {
            this.attributes.putAll( attributes );
            return this;
        }

        public KryptnosticUser build() {
            Preconditions.checkNotNull( this.domain );
            Preconditions.checkNotNull( this.username );
            Preconditions.checkState( !this.roles.isEmpty(), "User must be assigned to at least one role." );

            return new KryptnosticUser(
                    id,
                    Optional.of( domain ),
                    Optional.of( domain ),
                    username,
                    email,
                    Optional.of( certificate ),
                    groups,
                    roles,
                    attributes );
        }
    }

}
