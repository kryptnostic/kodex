package com.kryptnostic.heracles.authorization.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Permission {
    DENY, AUDIT, DISCOVER, WRITE, READ, OWNER;

    @JsonCreator
    public static Permission fromValue( int value ) {
        switch( value ) {
            case 1:
                return AUDIT;
            case 2:
                return DISCOVER;
            case 4:
                return WRITE;
            case 8:
                return READ;
            case 16:
                return OWNER;
            case 0:
            default:
                return DENY;
                    
        }
    }
    
    @JsonValue
    public int getValue() {
        switch (this) {
            case AUDIT:
                return 1;
            case DISCOVER:
                return 2;
            case WRITE:
                return 4;
            case READ:
                return 8;
            case OWNER:
                return 16;
            case DENY:
            default:
                return 0;
        }
    }
    
    
}
