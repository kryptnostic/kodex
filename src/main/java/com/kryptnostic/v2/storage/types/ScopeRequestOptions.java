package com.kryptnostic.v2.storage.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;

public class ScopeRequestOptions {
    public static final boolean     LOAD_ALL_TYPES_DEFAULT = true;
    private static final String     LOAD_ALL_TYPES_FIELD   = "loadAllTypes";
    private final Optional<Integer> page;
    private final boolean           loadAllTypes;

    public ScopeRequestOptions() {
        this( Optional.<Integer> absent(), Optional.<Boolean> absent() );
    }

    public ScopeRequestOptions(
            @JsonProperty( Names.PAGE_FIELD ) Optional<Integer> page,
            @JsonProperty( LOAD_ALL_TYPES_FIELD ) Optional<Boolean> loadAllTypes) {
        this.page = page;
        this.loadAllTypes = loadAllTypes.or( LOAD_ALL_TYPES_DEFAULT );
    }

    @JsonProperty( Names.PAGE_FIELD )
    public Optional<Integer> getPage() {
        return page;
    }

    @JsonProperty( LOAD_ALL_TYPES_FIELD )
    public boolean isLoadAllTypes() {
        return loadAllTypes;
    }

}
