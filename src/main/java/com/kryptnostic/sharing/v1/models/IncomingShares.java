package com.kryptnostic.sharing.v1.models;

import java.util.HashMap;

public final class IncomingShares extends HashMap<String, Share> {
    private static final long serialVersionUID = -4163211276862529808L;

    public IncomingShares() {
        super();
    }

    public IncomingShares( int initialCapacity ) {
        super( initialCapacity );
    }
}
