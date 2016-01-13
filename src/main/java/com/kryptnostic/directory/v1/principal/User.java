package com.kryptnostic.directory.v1.principal;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.KryptnosticUser;
import com.kryptnostic.kodex.v1.models.UserAttributes;

/**
 * Interface for user principals
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonSerialize(
    as = User.class )
@JsonDeserialize(
    as = KryptnosticUser.class )
public interface User extends Principal {
    @JsonProperty( Names.USERNAME_FIELD )
    String getName();

    @Deprecated
    @JsonProperty( Names.REALM_FIELD )
    String getRealm();

    @JsonProperty( Names.DOMAIN_FIELD )
    String getDomain();

    @Nonnull
    @JsonProperty( Names.EMAIL_FIELD )
    String getEmail();

    @Nonnull
    @JsonProperty( Names.CERTIFICATE_FIELD )
    byte[] getCertificate();

    @Nonnull
    @JsonIgnore
    Set<String> getRoles();

    @Nonnull
    @JsonIgnore
    Set<UUID> getGroups();

    @Nonnull
    @JsonIgnore
    int getUserVersion();

    @JsonProperty( Names.ATTRIBUTES_FIELD )
    UserAttributes getAttributes();

    @Nullable
    @JsonIgnore
    Optional<String> getGivenName();

    @Nullable
    @JsonIgnore
    Optional<String> getFamilyName();

    Optional<Object> getAttribute( String key );

}
