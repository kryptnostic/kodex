package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Padding {
    NONE(CipherConstants.NO_PADDING),
    PKCS5(CipherConstants.PKCS5_PADDING),
    OAEPWithSHA1AndMGF1Padding(CipherConstants.OAEPWithSHA1AndMGF1Padding),
    OAEPWithSHA256AndMGF1Padding(CipherConstants.OAEPWithSHA256AndMGF1Padding);
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
        } else if ( padding.equals( CipherConstants.OAEPWithSHA1AndMGF1Padding ) ) {
            return OAEPWithSHA1AndMGF1Padding;
        } else if ( padding.equals( CipherConstants.OAEPWithSHA256AndMGF1Padding ) ) {
            return OAEPWithSHA256AndMGF1Padding;
        }
        
        throw new InvalidParameterException( "Invalid padding: " + padding );
    }
}
