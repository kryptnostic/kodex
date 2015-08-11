package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.registration.v1.models.UserCreationRequest;

public interface RegistrationApi {
    public static final String CONTROLLER = "/registration";
    public static final String USER       = "/user";
    public static final String DEVELOPERS = "/developers";

    /**
     * Registers a normal user using the domain of their e-mail as their domain.
     *
     * @param request
     * @return
     */
    @POST( CONTROLLER + USER )
    UUID register( @Body UserCreationRequest request );

}
