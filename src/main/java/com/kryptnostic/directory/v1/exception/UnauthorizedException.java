package com.kryptnostic.directory.v1.exception;

public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 7114484600049851680L;
    
    public UnauthorizedException() {
        super("Authorization Failure!");
    }
    public UnauthorizedException(String message) {
        super(message);
    }

}
