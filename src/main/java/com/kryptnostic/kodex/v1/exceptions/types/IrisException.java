package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

public class IrisException extends Exception {
    public IrisException() {

    }

    public IrisException( Throwable e ) {
        super( e );
    }

    public IrisException( String msg ) {
        super( msg );
    }

    public IrisException( RetrofitError cause ) {
        super( cause );
    }
}
