package com.kryptnostic.heracles.v1.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Padding {
    NONE(CipherConstants.NO_PADDING),
    PKCS5(CipherConstants.PKCS5_PADDING);

    private final String padding;
    
    private Padding( String padding ) {
       this.padding = padding; 
    }
    
    @JsonValue
    public String getValue() {
        return padding;
    }
    
    @JsonCreator
    public static Padding fromString( String padding ) {
        if( padding.equals( CipherConstants.NO_PADDING ) ) {
            return NONE;
        } else if ( padding.equals( CipherConstants.PKCS5_PADDING ) ) {
            return PKCS5;
        }
        throw new InvalidParameterException( "Invalid padding: " + padding );
    }
}
