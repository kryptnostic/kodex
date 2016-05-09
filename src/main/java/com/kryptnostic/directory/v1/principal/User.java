package com.kryptnostic.directory.v1.principal;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.KryptnosticUser;

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
    @JsonProperty( Names.ROLES_FIELD )
    Set<String> getRoles();

    @Nonnull
    @JsonProperty( Names.GROUPS_PROPERTY )
    Set<UUID> getGroups();

    @Nonnull
    @JsonProperty( Names.CONFIRMATION_STATUS_FIELD )
    boolean getConfirmationStatus();

}
