package com.kryptnostic.storage.v2.types;

import java.util.UUID;

public final class TypeUUIDs {
    private TypeUUIDs() {}

    /**
     * The default object type
     */
    public static final UUID DEFAULT = new UUID( 0L, 0L );
    public static final UUID TYPE    = new UUID( 0L, 1L );
}
