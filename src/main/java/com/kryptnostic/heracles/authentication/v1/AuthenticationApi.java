package com.kryptnostic.heracles.authentication.v1;

import retrofit.http.Body;
import retrofit.http.POST;

import com.google.common.base.Optional;
import com.kryptnostic.heracles.principals.v1.User;

/**
 * The purpose of this class is to enable applications to provide an authentication experience wrapped around
 * Kryptnostic services. With this class it is possible to authenticate a user to make sure that provided credentials
 * are correct before proceeding with subsequent REST calls.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public interface AuthenticationApi {
    String CONTROLLER   = "/authentication";

    String LOGIN = "/login";
    String CERTIFICATE  = "/certificate";


    /**
     * Performs a password based authentication against the server. This 
     * doesn't require any special protection against CSRF as any attacker 
     * capable of forging the attack will already have the user's credentials.
     * 
     * @param request AuthenticationRequest
     * @return User information
     */
    @POST(LOGIN)
    Optional<User> authenticate( @Body AuthenticationRequest request );
    
}
