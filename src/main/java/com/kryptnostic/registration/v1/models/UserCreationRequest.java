package com.kryptnostic.registration.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.UserAttributes;

public class UserCreationRequest {
    private final String                   password;
    private final String                   email;
    private final Optional<UserAttributes> attributes;
    private final boolean                  confirmationEmailNeeded;

    @JsonCreator
    public UserCreationRequest(
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.ATTRIBUTES_FIELD ) Optional<UserAttributes> attributes,
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailNeeded ) {
        this.password = password;
        this.email = email;
        this.confirmationEmailNeeded = confirmationEmailNeeded.or( false );
        this.attributes = attributes;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.GIVEN_NAME_FIELD )
    public Optional<String> getFamilyName() {
        return attributes.isPresent() ? Optional.fromNullable( attributes.get().get( Names.GIVEN_NAME_FIELD ) )
                : Optional.<String> absent();
    }

    @JsonProperty( Names.FAMILY_NAME_FIELD )
    public Optional<String> getGivenName() {
        return attributes.isPresent() ? Optional.fromNullable( attributes.get().get( Names.FAMILY_NAME_FIELD ) )
                : Optional.<String> absent();
    }

    @JsonProperty( Names.ATTRIBUTES_FIELD )
    public Optional<UserAttributes> getAttributes() {
        return attributes;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailNeeded() {
        return confirmationEmailNeeded;
    }
}
