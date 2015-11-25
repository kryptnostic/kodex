package com.kryptnostic.v2.sharing.models;

import java.util.HashSet;
import java.util.Set;


public final class IncomingShares extends HashSet<Share> {

    private static final long serialVersionUID = -4163211276862529808L;

    public IncomingShares() {
        super();
    }

    public IncomingShares( Set<Share> deserialize ) {
        super( deserialize );
    }
}
