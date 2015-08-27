package com.kryptnostic.authentication.v1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class EmailAuthenticationRequest {
    private final String email;
    private final String password;

    @JsonCreator
    public EmailAuthenticationRequest(
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.PASSWORD_FIELD ) String password ) {
        this.email = email;
        this.password = password;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest [password=*******" + ", uuid=" + ( email == null ? "null" : email.toString() )
                + "]";
    }
}