package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.directory.v1.exception.AddUserException;
import com.kryptnostic.registration.v1.models.UserCreationRequest;

public interface RegistrationApi {
    String CONTROLLER = "/registration";
    String USER       = "/user";

    /**
     * Registers a normal user using the domain of their e-mail as their domain.
     *
     * @param request
     * @return
     * @throws AddUserException
     */
    @POST( CONTROLLER + USER )
    UUID register( @Body UserCreationRequest request ) throws AddUserException;

}
