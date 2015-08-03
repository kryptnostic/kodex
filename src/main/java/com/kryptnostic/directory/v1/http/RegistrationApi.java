package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.directory.v1.exception.DeveloperAlreadyExistsException;
import com.kryptnostic.directory.v1.exception.InvalidEmailException;
import com.kryptnostic.directory.v1.exception.MailException;
import com.kryptnostic.directory.v1.exception.ReservationTakenException;
import com.kryptnostic.directory.v1.model.DeveloperRegistration;
import com.kryptnostic.directory.v1.model.request.DeveloperRegistrationRequest;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.registration.v1.models.UserRegistrationRequest;

public interface RegistrationApi {
    String CONTROLLER = "/registration";
    String DEVELOPERS = "/developers";
    String USER       = "/user";

    /**
     * Registers a normal user using the domain of their e-mail as their domain.
     * 
     * @param request
     * @return
     */
    @POST( USER )
    UUID register( @Body UserRegistrationRequest request );

}
