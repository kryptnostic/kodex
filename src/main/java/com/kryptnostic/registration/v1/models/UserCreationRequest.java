package com.kryptnostic.registration.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v2.model.ResourcesRequest;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.models.UserAttributes;

public class UserCreationRequest {
    private final String                     password;
    private final String                     email;
    private final String                     name;
    private final Optional<UserAttributes>   attributes;
    private final boolean                    confirmationEmailNeeded;
    private final Optional<ResourcesRequest> resourcesRequest;

    @JsonCreator
    public UserCreationRequest(
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.NAME_FIELD ) String name,
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.ATTRIBUTES_FIELD ) Optional<UserAttributes> attributes,
            @JsonProperty( Names.CONFIRMATION_FIELD ) Optional<Boolean> confirmationEmailNeeded,
            @JsonProperty( com.kryptnostic.v2.constants.Names.RESOURCES_REQUEST ) Optional<ResourcesRequest> resourcesRequest) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.confirmationEmailNeeded = confirmationEmailNeeded.or( false );
        this.attributes = attributes;
        this.resourcesRequest = resourcesRequest;
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

    @JsonProperty( Names.ATTRIBUTES_FIELD )
    public Optional<UserAttributes> getAttributes() {
        return attributes;
    }

    @JsonProperty( Names.CONFIRMATION_FIELD )
    public boolean isConfirmationEmailNeeded() {
        return confirmationEmailNeeded;
    }

    @JsonProperty( com.kryptnostic.v2.constants.Names.RESOURCES_REQUEST )
    public Optional<ResourcesRequest> getResoucesRequest() {
        return resourcesRequest;
    }

}
