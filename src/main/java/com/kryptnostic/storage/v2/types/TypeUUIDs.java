package com.kryptnostic.storage.v2.types;

import java.util.UUID;

public final class TypeUUIDs {
    private TypeUUIDs() {}

    /**
     * The default object type
     */
    public static final UUID TYPE           = new UUID( 0L, 0L );
    public static final UUID UTF8_STRING    = new UUID( 0L, 1L );
    public static final UUID UTF16_STRING   = new UUID( 0L, 2L );
    public static final UUID INDEX_METADATA = new UUID( 0L, 3L );

    public static final UUID DEFAULT_TYPE   = UTF8_STRING;
}
