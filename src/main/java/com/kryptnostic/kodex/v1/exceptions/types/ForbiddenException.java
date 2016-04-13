package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

@SuppressWarnings( "serial" )
public class ForbiddenException extends Exception {
    
    public ForbiddenException() {
        super();
    }
    
    public ForbiddenException( String message ) {
        super( message );
    }
    
    public ForbiddenException( RetrofitError cause) {
        super( cause);
    }
}
