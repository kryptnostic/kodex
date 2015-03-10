package com.kryptnostic.directory.v1.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.model.ReservationToken;
import com.kryptnostic.kodex.v1.constants.Names;

public final class ReserveUserResponse {
    private final ReservationToken token;

    @JsonCreator
    public ReserveUserResponse( @JsonProperty( Names.TOKEN_PROPERTY ) ReservationToken token ) {
        this.token = token;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public ReservationToken getToken() {
        return token;
    }

}
