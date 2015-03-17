package com.kryptnostic.directory.v1.exception;

public class ReservationTakenException extends Exception {
    private static final long serialVersionUID = 8946007712630136459L;

    public ReservationTakenException() {
        super( "The registration for this user has already been reserved" );
    }
}
