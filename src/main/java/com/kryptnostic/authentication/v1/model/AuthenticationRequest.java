package com.kryptnostic.authentication.v1.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class AuthenticationRequest {
    private final String password;
    private final UUID   id;

    @JsonCreator
    public AuthenticationRequest(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.PASSWORD_FIELD ) String password ) {
        this.id = id;
        this.password = password;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getId() {
        return id;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest [password=*******" + ", uuid=" + id.toString() + "]";
    }
}
