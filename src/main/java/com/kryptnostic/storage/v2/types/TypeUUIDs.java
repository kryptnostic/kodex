package com.kryptnostic.storage.v2.types;

import java.util.UUID;

public final class TypeUUIDs {
    private TypeUUIDs() {}

    public static final UUID DEFAULT  = new UUID( 0L, 0L );
    public static final UUID TYPE     = new UUID( 0L, 1L );
    public static final UUID METADATA = new UUID( 0L, 2L );
}
