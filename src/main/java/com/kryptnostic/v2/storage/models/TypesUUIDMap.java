package com.kryptnostic.v2.storage.models;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.ImmutableMap;

public class TypesUUIDMap extends LinkedHashMap<String, UUID> {
    private static final long serialVersionUID = -3836960389525657170L;

    public TypesUUIDMap() {
        super();
    }

    public TypesUUIDMap( Map<String, UUID> types ) {
        super( ImmutableMap.copyOf( types ) );
    }

}
