package com.kryptnostic.directory.v1.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.principal.User;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.UserAttributes;

/**
 * Request to update {@link User} data.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public final class UpdateUserRequest {

    private final Optional<String>         email;
    private final Optional<String>         name;
    private final Optional<String>         password;
    private final Optional<UserAttributes> attributes;
    private final Optional<byte[]>         certificate;

    @JsonCreator
    public UpdateUserRequest(
            @JsonProperty( Names.EMAIL_FIELD ) Optional<String> email,
            @JsonProperty( Names.USERNAME_FIELD ) Optional<String> name,
            @JsonProperty( Names.ATTRIBUTES_FIELD ) Optional<UserAttributes> attributes,
            @JsonProperty( Names.PASSWORD_FIELD ) Optional<String> password,
            @JsonProperty( Names.CERTIFICATE_FIELD ) Optional<byte[]> certificate ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.certificate = certificate;
        this.attributes = attributes;
    }

    @JsonProperty( Names.USERNAME_FIELD )
    public Optional<String> getName() {
        return name;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public Optional<String> getEmail() {
        return email;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public Optional<String> getPassword() {
        return password;
    }

    @JsonProperty( Names.CERTIFICATE_FIELD )
    public Optional<byte[]> getCertificate() {
        return certificate;
    }

    @JsonProperty( Names.ATTRIBUTES_FIELD )
    public Optional<UserAttributes> getAttributes() {
        return attributes;
    }

}
