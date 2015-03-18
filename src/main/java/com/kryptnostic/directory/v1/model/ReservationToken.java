package com.kryptnostic.directory.v1.model;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public final class ReservationToken {
    private static final SecureRandom random        = new SecureRandom();
    private static final Integer      N_SECURE_BITS = 130;
    private static final Integer      ENCODING      = 32;
    private final String              value;

    @JsonCreator
    private ReservationToken( String value ) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ReservationToken getSecureToken() {
        return new ReservationToken( new BigInteger( N_SECURE_BITS, random ).toString( ENCODING ) );
    }

    public static ReservationToken fromValue( final String value ) {
        return new ReservationToken( value );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ReservationToken other = (ReservationToken) obj;
        if ( value == null ) {
            if ( other.value != null ) return false;
        } else if ( !value.equals( other.value ) ) return false;
        return true;
    }

}
