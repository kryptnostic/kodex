package com.kryptnostic.kodex.v1.models;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserAttributes extends ConcurrentHashMap<String, Object> {
    private static final long serialVersionUID = -5350734289538969370L;

    public UserAttributes() {
        super();
    }

    public UserAttributes( ConcurrentMap<String, Object> attributes ) {
        super( attributes );
    }

    public UserAttributes( int initialCapacity ) {
        super( initialCapacity );
    }
}
