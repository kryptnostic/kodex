package com.kryptnostic.kodex.v1.models;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.FAMILY_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GIVEN_NAME_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.ID_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.PASSWORD_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.ROLES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.USERNAME_FIELD;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

/**
 * Model for a Heracles user.
 * @author Nick Hewitt, Ryan Buckheit
 */
@Immutable
public final class KryptnosticUser implements User, Serializable {

    private static final long serialVersionUID = 667467236790964696L;

    private final UUID                  id;
    private final String                realm;
    private final String                username;
    private final String                email;
    private final String                givenName;
    private final String                familyName;
    private final String                password;
    private final byte[]                certificate;
    private final ImmutableSet<UUID>    groups;
    private final ImmutableSet<String>  roles;
    private final ImmutableMap<String, String> attributes;

    @JsonCreator
    public KryptnosticUser(
            @JsonProperty( ID_FIELD ) UUID id,
            @JsonProperty( REALM_FIELD ) String realm,
            @JsonProperty( USERNAME_FIELD ) String username,
            @JsonProperty( GIVEN_NAME_FIELD ) String givenName,
            @JsonProperty( FAMILY_NAME_FIELD ) String familyName,
            @JsonProperty( EMAIL_FIELD ) String email,
            @JsonProperty( PASSWORD_FIELD ) String password,
            @JsonProperty( CERTIFICATE_PROPERTY ) Optional<byte[]> certificate,
            @JsonProperty( GROUPS_PROPERTY ) Set<UUID> groups,
            @JsonProperty( ROLES_FIELD ) Set<String> roles,
            @JsonProperty( ATTRIBUTES_FIELD ) Map<String, String> attributes ) {
        this.id = id;
        this.realm = realm;
        this.username = username;
        this.password = password;
        this.certificate = certificate.or( new byte[ 0 ] );
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.roles = ImmutableSet.copyOf( roles );
        this.groups = ImmutableSet.copyOf( groups );
        this.attributes = ImmutableMap.copyOf( attributes );
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
        return this.realm;
    }

    @Override
    public String getAttribute( String key ) {
        return attributes.get( key );
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "HeraclesUser [id=" + id + ",username=" + username + ",password=" + password + ", certificate="
                + Arrays.toString( certificate ) + ", groups=" + groups + ", attributes=" + attributes + ", roles=" + roles + "]";
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
        public Set<UUID>           groups;
        public Set<String>         roles;
        public Map<String, String> attributes;

        // TODO add created and update dates

        public HeraclesUserBuilder( String realm, String username ) {
            this.realm = realm;
            this.username = username;
            this.groups = Sets.newConcurrentHashSet();
            this.attributes = Maps.newConcurrentMap();
            this.roles = Sets.newConcurrentHashSet();
            this.givenName = "";
            this.familyName = "";
            this.certificate = new byte[0];
        }

        public HeraclesUserBuilder( String email ) {
            this.email = email;
            this.username = email;
            this.givenName = "";
            this.familyName = "";
            this.certificate = new byte[0];
            this.groups = Sets.newConcurrentHashSet();
            this.attributes = Maps.newConcurrentMap();
            this.roles = Sets.newConcurrentHashSet();
        }

        public HeraclesUserBuilder withPassword( String password ) {
            this.password = password;
            return this;
        }

        public HeraclesUserBuilder withRealm( String realm ) {
            this.realm = realm;
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
            Preconditions.checkNotNull( this.realm );
            Preconditions.checkNotNull( this.username );
            Preconditions.checkNotNull( this.password );
            Preconditions.checkState( !this.roles.isEmpty(), "User must be assigned to at least one role." );

            return new KryptnosticUser( this.id,
                    this.realm,
                    this.username,
                    this.givenName,
                    this.familyName,
                    this.email,
                    this.password,
                    Optional.of( this.certificate ),
                    ImmutableSet.copyOf( this.groups ),
                    ImmutableSet.copyOf( this.roles ),
                    ImmutableMap.copyOf( this.attributes ) );
        }
    }

}
