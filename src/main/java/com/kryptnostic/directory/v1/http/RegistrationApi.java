package com.kryptnostic.directory.v1.http;

import retrofit.http.Body;
import retrofit.http.POST;

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
     */
    @POST( DEVELOPERS )
    DeveloperRegistration register( @Body DeveloperRegistrationRequest request ); // public

}
