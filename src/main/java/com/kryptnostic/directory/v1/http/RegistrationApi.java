package com.kryptnostic.directory.v1.http;

import java.util.UUID;

import com.kryptnostic.directory.v1.exception.AddUserException;
import com.kryptnostic.mail.models.EmailRequest;
import com.kryptnostic.registration.v1.models.UserCreationRequest;
import com.squareup.okhttp.Response;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface RegistrationApi {
    String CONTROLLER   = "/registration";

    String UUID         = "userId";
    String TOKEN        = "token";
    String RESEND       = "/resend";

    String USER         = "/user";
    String INVITATION   = "/invitation";
    String CONFIRMATION = "/confirmation";
    String VERIFICATION = "/verification";
    String UUID_PATH    = "/{" + UUID + "}";
    String TOKEN_PATH   = "/{" + TOKEN + "}";

    /**
     * Registers a normal user using the domain of their e-mail as their domain.
     *
     * @param request
     * @return
     * @throws AddUserException
     */
    @POST( CONTROLLER + USER )
    UUID register( @Body UserCreationRequest request );

    @GET( CONTROLLER + CONFIRMATION + RESEND )
    Response resendConfirmationEmail();

    @GET( CONTROLLER + VERIFICATION + UUID_PATH + TOKEN_PATH )
    boolean verifyConfirmation( @Path( UUID ) UUID userId, @Path( TOKEN ) String token);

    @POST( CONTROLLER + INVITATION )
    Response sendInvitationEmail( @Body EmailRequest emailRequest );
}
