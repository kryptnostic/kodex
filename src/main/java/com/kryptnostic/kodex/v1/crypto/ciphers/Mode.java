package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Mode {
    CTR( CipherConstants.CTR_MODE ),
    ECB( CipherConstants.ECB_MODE );
    
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
        } else if( mode.equals( CipherConstants.ECB_MODE ) ) {
            return ECB;
        }
        throw new InvalidParameterException( "Invalid mode: " + mode ); 
    }
}
