package com.kryptnostic.directory.v1.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Request to update {@link User} data. Username and password may be updated. If either field has a different value than
 * that of the current, authenticated user, then it will be substituted. The new username must be available to be
 * updated. Groups and attributes are not updatable from this request.
 * 
 * @author Nick Hewitt
 *
 */
public final class UpdateUserRequest {
    private final Optional<String> username;
    private final Optional<String> givenName;
    private final Optional<String> familyName;
    private final Optional<String> email;
    private final Optional<byte[]> password;
    private final Optional<byte[]> certificate;

    @JsonCreator
    public UpdateUserRequest(
            @JsonProperty( Names.NAME_FIELD ) Optional<String> username,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) Optional<String> givenName,
            @JsonProperty( Names.FAMILY_NAME_FIELD ) Optional<String> familyName,
            @JsonProperty( Names.EMAIL_FIELD ) Optional<String> email,
            @JsonProperty( Names.PASSWORD_FIELD ) Optional<byte[]> password,
            @JsonProperty( Names.CERTIFICATE_PROPERTY ) Optional<byte[]> certificate ) {
        this.username = username;
        this.givenName = givenName;
        this.familyName = familyName;
        this.email = email;
        this.password = password;
        this.certificate = certificate;
    }

    @JsonProperty( Names.NAME_FIELD )
    public Optional<String> getName() {
        return username;
    }

    @JsonProperty( Names.GIVEN_NAME_FIELD )
    public Optional<String> getGivenName() {
        return givenName;
    }

    @JsonProperty( Names.FAMILY_NAME_FIELD )
    public Optional<String> getFamilyName() {
        return familyName;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public Optional<String> getEmail() {
        return email;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public Optional<byte[]> getPassword() {
        return password;
    }

    @JsonProperty( Names.CERTIFICATE_PROPERTY )
    public Optional<byte[]> getCertificate() {
        return certificate;
    }

}
