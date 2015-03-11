package com.kryptnostic.authentication.v1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class AuthenticationToken {
    private final UserKey user;
    private final String  token;

    @JsonCreator
    public AuthenticationToken(
            @JsonProperty( Names.USER_FIELD ) UserKey user,
            @JsonProperty( Names.TOKEN_PROPERTY ) String token ) {
        this.user = user;
        this.token = token;
    }

    @JsonProperty( Names.USER_FIELD )
    public UserKey getUser() {
        return user;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public String getToken() {
        return token;
    }
}
