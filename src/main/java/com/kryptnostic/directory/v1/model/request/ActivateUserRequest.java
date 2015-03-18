package com.kryptnostic.directory.v1.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.model.ReservationToken;
import com.kryptnostic.kodex.v1.constants.Names;

public class ActivateUserRequest {
    private final ReservationToken reservationToken;
    private final String           realm;
    private final String           username;
    private final String           password;

    @JsonCreator
    public ActivateUserRequest(
            @JsonProperty( Names.TOKEN_PROPERTY ) ReservationToken reservationToken,
            @JsonProperty( Names.REALM_FIELD ) String realm,
            @JsonProperty( Names.NAME_FIELD ) String username,
            @JsonProperty( Names.PASSWORD_FIELD ) String password ) {
        this.reservationToken = reservationToken;
        this.realm = realm;
        this.username = username;
        this.password = password;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public ReservationToken getReservationToken() {
        return reservationToken;
    }

    @JsonProperty( Names.REALM_FIELD )
    public String getRealm() {
        return realm;
    }

    @JsonProperty( Names.NAME_FIELD )
    public String getUsername() {
        return username;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }
}
