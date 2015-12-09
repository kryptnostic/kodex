package com.kryptnostic.mail.v1.http;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.mail.v1.models.EmailRequest;

public interface MailServiceApi {
    String CONTROLLER = "/mail";
    String INVITE     = "/invite";

    @Deprecated
    @POST( CONTROLLER + INVITE )
    BasicResponse<String> sendInvitationEmail( @Body EmailRequest request );

}