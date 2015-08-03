package com.kryptnostic.registration.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class UserRegistrationRequest {
    private final String           password;
    private final String           email;
    private final Optional<String> givenName;
    private final Optional<String> familyName;
    private final boolean          confirmationEmailNeeded;

    public UserRegistrationRequest(
            @JsonProperty( Names.EMAIL_FIELD ) final String email,
            @JsonProperty( Names.PASSWORD_FIELD ) final String password,
            @JsonProperty( Names.CONFIRMATION_FIELD ) final Optional<Boolean> confirmationEmailNeeded,
            @JsonProperty( Names.GIVEN_NAME_FIELD ) final Optional<String> givenName,
            @JsonProperty( Names.FAMILY_NAME_FIELD ) final Optional<String> familyName ) {
        this.password = password;
        this.email = email;
        this.confirmationEmailNeeded = confirmationEmailNeeded.or( false );
        this.givenName = givenName;
        this.familyName = familyName;
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
        return familyName;
    }

    @JsonProperty( Names.FAMILY_NAME_FIELD )
    public Optional<String> getGivenName() {
        return givenName;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailNeeded() {
        return confirmationEmailNeeded;
    }
}
