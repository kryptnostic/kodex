package com.kryptnostic.directory.v1.model;

import java.util.EnumSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatus {
    OPEN(0),
    APPROVED(1),
    DENIED(2);

    private static final RequestStatus[] valArray = RequestStatus.values();
    private final int value;

    private RequestStatus( int value ) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static RequestStatus fromValue( int value ) {
        return valArray[ value ];
    }
    
    public static Set<RequestStatus> setOfAll() {
        return EnumSet.allOf( RequestStatus.class );
    }
}
