package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Mode {
    CTR( CipherConstants.CTR_MODE ), ECB( CipherConstants.ECB_MODE ), CBC( CipherConstants.CBC_MODE ), GCM(
            CipherConstants.GCM_MODE ),
    NONE( "" );

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
        if ( mode.equals( CipherConstants.CTR_MODE ) ) {
            return CTR;
        } else if ( mode.equals( CipherConstants.ECB_MODE ) ) {
            return ECB;
        } else if ( mode.equals( CipherConstants.GCM_MODE ) ) {
            return GCM;
        } else if ( mode.equals( CipherConstants.CBC_MODE ) ) {
            return CBC;
        }
        throw new InvalidParameterException( "Invalid mode: " + mode );
    }
}
