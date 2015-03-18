package com.kryptnostic.directory.v1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    ACTIVE( 0 ), RESERVED( 1 );

    private static final UserStatus[] statuses = UserStatus.values();
    private final int                 value;

    private UserStatus( int value ) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static UserStatus fromValue( int value ) {
        return statuses[ value ];
    }
}
