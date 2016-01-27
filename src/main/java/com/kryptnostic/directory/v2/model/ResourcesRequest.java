package com.kryptnostic.directory.v2.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ResourcesRequest extends LinkedHashMap<String, Long> {
    private static final long serialVersionUID = 2373322922110759415L;

    public ResourcesRequest() {
        super();
    }

    public ResourcesRequest( Map<String, Long> request ) {
        super( ImmutableMap.copyOf( request ) );
    }
}
