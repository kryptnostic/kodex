package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

public class KodexException extends Exception {
    private static final long serialVersionUID = 4074111549719125522L;

    public KodexException() {

    }

    public KodexException( Throwable e ) {
        super( e );
    }

    public KodexException( String msg ) {
        super( msg );
    }

    public KodexException( RetrofitError cause ) {
        super( cause );
    }
}