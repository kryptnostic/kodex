package com.kryptnostic.kodex.v1.exceptions.types;

import retrofit.RetrofitError;

public class IrisException extends Exception {
    private static final long serialVersionUID = -7047837475537390107L;

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
