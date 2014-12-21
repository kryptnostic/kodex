package com.kryptnostic.heracles.registration.v1.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RegistrationResult {
    SUCCESS(0),
    REALM_EXISTS(1),
    INVALID_REALM_NAME(2),
    INVALID_USERNAME(3);
    
    private static final RegistrationResult[] values = RegistrationResult.values(); 
    private final int value;
    
    private RegistrationResult( int value ) {
        this.value = value;
    }
    
    @JsonValue
    public int getValue() {
        return value;
    }
    
    @JsonCreator
    public static RegistrationResult fromValue( int value ) {
        return values[ value ]; 
    }
}
