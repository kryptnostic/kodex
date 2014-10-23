package com.kryptnostic.kodex.v1.authentication;

import retrofit.RequestInterceptor;

import com.kryptnostic.users.v1.UserKey;

public class PreauthenticationRequestInterceptor implements RequestInterceptor {
    private UserKey user;
    private String  credential;

    public PreauthenticationRequestInterceptor() {
        this.user = null;
        this.credential = null;
    }

    public PreauthenticationRequestInterceptor( UserKey user, String credential ) {
        this.user = user;
        this.credential = credential;
    }

    public void updateCredentials( UserKey user, String credential ) {
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
