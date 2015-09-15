package com.kryptnostic.directory.v1.principal;

import static com.kryptnostic.kodex.v1.constants.Names.ATTRIBUTES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.CERTIFICATE_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.DOMAIN_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.EMAIL_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.GROUPS_PROPERTY;
import static com.kryptnostic.kodex.v1.constants.Names.REALM_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.ROLES_FIELD;
import static com.kryptnostic.kodex.v1.constants.Names.USERNAME_FIELD;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.models.KryptnosticUser;
import com.kryptnostic.kodex.v1.models.UserAttributes;

/**
 * Interface for user principals
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonDeserialize(
    as = KryptnosticUser.class )
@JsonSerialize(as = User.class)
public interface User extends Principal {
    @JsonProperty( USERNAME_FIELD )
    String getName();

    @Deprecated
    @JsonProperty( REALM_FIELD )
    String getRealm();

    @JsonProperty( DOMAIN_FIELD )
    String getDomain();

    @Nonnull
    @JsonProperty( EMAIL_FIELD )
    String getEmail();

    @Nonnull
    @JsonProperty( CERTIFICATE_FIELD )
    byte[] getCertificate();

    @Nonnull
    @JsonProperty( ROLES_FIELD )
    Set<String> getRoles();

    @Nonnull
    @JsonProperty( GROUPS_PROPERTY )
    Set<UUID> getGroups();

    @JsonProperty( ATTRIBUTES_FIELD )
    UserAttributes getAttributes();

    @Nullable
    @JsonIgnore
    Optional<String> getGivenName();

    @Nullable
    @JsonIgnore
    Optional<String> getFamilyName();

    Optional<String> getAttribute( String key );
}
