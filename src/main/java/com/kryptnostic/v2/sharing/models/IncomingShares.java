package com.kryptnostic.v2.sharing.models;

import java.util.HashMap;

import com.kryptnostic.v2.storage.models.VersionedObjectKey;


//TODO: Write stream serializer for v2 incoming shares.
public final class IncomingShares extends HashMap<VersionedObjectKey,Share> {
    private static final long serialVersionUID = -4163211276862529808L;
}
