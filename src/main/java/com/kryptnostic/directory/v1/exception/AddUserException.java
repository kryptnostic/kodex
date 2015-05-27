package com.kryptnostic.directory.v1.exception;

public class AddUserException extends Exception {
    public AddUserException( String string ) {
        super( string );
    }

    public AddUserException() {
        super();
    }

    public AddUserException( Throwable e ) {
        super( e );
    }

    private static final long serialVersionUID = 8293439945393358900L;
}