package com.kryptnostic.directory.v1.exception;

public class UserUpdateException extends Exception {
    private static final long serialVersionUID = 8161439362290479438L;

    public UserUpdateException() {
        super();
    }

    public UserUpdateException( String message ) {
        super( message );
    }

    public UserUpdateException( Throwable t ) {
        super( t );
    }
}
