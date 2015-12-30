package com.kryptnostic.v2.storage.types;

import java.util.UUID;

public final class TypeUUIDs {
    private TypeUUIDs() {}

    /**
     * The default object type
     */
    public static final UUID TYPE                   = new UUID( 0L, 0L );
    public static final UUID UTF8_STRING            = new UUID( 0L, 1L );
    public static final UUID UTF16_STRING           = new UUID( 0L, 2L );
    public static final UUID INDEX_METADATA         = new UUID( 0L, 3L );

    /**
     * A block is a reserved type for chunking a large object into several child objects.
     */
    public static final UUID BLOCK                  = new UUID( 0L, 4L );
    public static final UUID FHE_PRIVATE_KEY        = new UUID( 0L, 5L );
    public static final UUID FHE_SEARCH_PRIVATE_KEY = new UUID( 0L, 6L );

    public static final UUID DEFAULT_TYPE           = UTF8_STRING;
}
