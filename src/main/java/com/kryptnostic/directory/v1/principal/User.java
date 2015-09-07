package com.kryptnostic.directory.v1.principal;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.models.UserAttributes;

/**
 * Interface for user principals
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class" )
public interface User extends Principal {
    String getName();

    @Deprecated
    String getRealm();
    
    String getDomain();

    @Nonnull
    String getEmail();

    @Nonnull
    byte[] getCertificate();

    @Nonnull
    Set<String> getRoles();

    @Nonnull
    Set<UUID> getGroups();

    @Nullable
    @JsonIgnore
    Optional<String> getGivenName();

    @Nullable
    @JsonIgnore
    Optional<String> getFamilyName();

    UserAttributes getAttributes();

    Optional<String> getAttribute( String key );
}
