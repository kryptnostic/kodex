package com.kryptnostic.directory.v1.http;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.directory.v1.exception.DeveloperAlreadyExistsException;
import com.kryptnostic.directory.v1.exception.InvalidEmailException;
import com.kryptnostic.directory.v1.exception.ReservationTakenException;
import com.kryptnostic.directory.v1.model.DeveloperRegistration;
import com.kryptnostic.directory.v1.model.request.DeveloperRegistrationRequest;

public interface RegistrationApi {
    public static final String CONTROLLER = "/registration";
    public static final String DEVELOPERS = "/developers";

    /**
     * Register a developer for the Kryptnostic platform.
     * 
     * @param request {@link DeveloperRegistrationRequest}
     * @return {@link DeveloperRegistration}
     * @throws ReservationTakenException
     * @throws InvalidEmailException
     * @throws DeveloperAlreadyExistsException
     */
    @POST( DEVELOPERS )
    DeveloperRegistration register( @Body DeveloperRegistrationRequest request ) throws ReservationTakenException,
            DeveloperAlreadyExistsException, InvalidEmailException;

}
