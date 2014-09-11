package com.kryptnostic.heracles.v1.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Mode {
    CTR( CipherConstants.CTR_MODE );
    
    private final String mode;
    private Mode( String mode ) {
        this.mode = mode;
    }
    
    @JsonValue
    public String getValue() {
        return mode;
    }
    
    @JsonCreator
    public static Mode fromString( String mode ) {
        if( mode.equals( CipherConstants.CTR_MODE ) ) { 
            return CTR;
        }
        throw new InvalidParameterException( "Invalid mode: " + mode ); 
    }
}
