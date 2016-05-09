package com.kryptnostic.registration.v1.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class UserCreationRequest {
    private final String                      password;
    private final String                      email;
    private final String                      name;
    private final boolean                     confirmationEmailNeeded;
    private final Optional<Map<String, Long>> desiredResources;

    @JsonCreator
    public UserCreationRequest(
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.NAME_FIELD ) String name,
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailNeeded,
            @JsonProperty( com.kryptnostic.v2.constants.Names.DESIRED_RESOURCES_FIELD ) Optional<Map<String, Long>> desiredResources ) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.confirmationEmailNeeded = confirmationEmailNeeded.or( false );
        this.desiredResources = desiredResources;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.NAME_FIELD )
    public String getDisplayName() {
        return name;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailNeeded() {
        return confirmationEmailNeeded;
    }

    @JsonProperty( com.kryptnostic.v2.constants.Names.DESIRED_RESOURCES_FIELD )
    public Optional<Map<String, Long>> getDesiredResources() {
        return desiredResources;
    }

}
