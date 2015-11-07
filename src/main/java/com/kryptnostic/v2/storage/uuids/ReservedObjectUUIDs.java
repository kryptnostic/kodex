package com.kryptnostic.v2.storage.uuids;

import java.util.UUID;

public final class ReservedObjectUUIDs {
    private ReservedObjectUUIDs() {}
    public static final UUID PRIVATE_KEY                 = new UUID( 0L, 0L );
    public static final UUID SEARCH_PRIVATE_KEY          = new UUID( 0L, 1L );
    public static final UUID CLIENT_HASH_FUNCTION        = new UUID( 0L, 2L );    
}
