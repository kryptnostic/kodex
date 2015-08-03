package com.kryptnostic.directory.v1.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.model.ReservationToken;
import com.kryptnostic.kodex.v1.constants.Names;

public class ActivateUserRequest {
    private final ReservationToken reservationToken;
    private final String           email;
    private final String           password;

    @JsonCreator
    public ActivateUserRequest(
            @JsonProperty( Names.TOKEN_PROPERTY ) ReservationToken reservationToken,
            @JsonProperty( Names.EMAIL_FIELD ) String email,
            @JsonProperty( Names.PASSWORD_FIELD ) String password ) {
        this.reservationToken = reservationToken;
        this.email = email;
        this.password = password;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public ReservationToken getReservationToken() {
        return reservationToken;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public String getEmail() {
        return email;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public String getPassword() {
        return password;
    }

}
