package com.kryptnostic.kodex.v1.authentication;

import java.util.UUID;

import retrofit.RequestInterceptor;

public class PreauthenticationRequestInterceptor implements RequestInterceptor {
    private UUID user;
    private String  credential;

    public PreauthenticationRequestInterceptor() {
        this.user = null;
        this.credential = null;
    }

    public PreauthenticationRequestInterceptor( UUID user, String credential ) {
        this.user = user;
        this.credential = credential;
    }

    public void updateCredentials( UUID user, String credential ) {
        this.user = user;
        this.credential = credential;
    }

    @Override
    public void intercept( RequestFacade request ) {
        if ( user != null ) {
            request.addHeader( Headers.PRINCIPAL.toString(), user.toString() );
            request.addHeader( Headers.CREDENTIAL.toString(), credential );
        }
    }
}
