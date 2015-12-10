package com.kryptnostic.mail.v1.http;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.mail.v1.models.EmailRequest;

import retrofit.http.Body;
import retrofit.http.POST;

public interface MailServiceApi {

    String CONTROLLER = "invite";
    String INVITE     = "/**";

    @POST( CONTROLLER + INVITE )
    BasicResponse<String> sendInvitationEmail( @Body EmailRequest request );

}