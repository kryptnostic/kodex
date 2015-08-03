package com.kryptnostic.directory.v1.model.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ActivateUserResponse {
    private final String  password;
    private final UUID userKey;

    @JsonCreator
    public ActivateUserResponse(
            @JsonProperty( Names.PASSWORD_FIELD ) String password,
            @JsonProperty( Names.USER_FIELD ) UUID userKey ) {
        this.password = password;
        this.userKey = userKey;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUserKey() {
        return userKey;
    }

}
