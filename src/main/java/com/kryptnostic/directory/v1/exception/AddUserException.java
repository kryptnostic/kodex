package com.kryptnostic.directory.v1.exception;

public class AddUserException extends RuntimeException {
    public AddUserException( String string ) {
        super( string );
    }

    public AddUserException() {
        super();
    }

    private static final long serialVersionUID = 8293439945393358900L;
}
