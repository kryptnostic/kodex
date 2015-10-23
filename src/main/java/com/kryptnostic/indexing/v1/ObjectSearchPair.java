package com.kryptnostic.indexing.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class ObjectSearchPair {
    public static final String SEARCH_PAIR_FIELD = "searchPair";
    private final byte[]       objectSearchPair;

    @JsonCreator
    public ObjectSearchPair( @JsonProperty( SEARCH_PAIR_FIELD ) byte[] objectSearchPair ) {
        Preconditions.checkState( objectSearchPair.length == 2080, "Index pair must be 2080 bytes long." );
        this.objectSearchPair = objectSearchPair;
    }

    @JsonProperty( SEARCH_PAIR_FIELD )
    public byte[] getSearchPair() {
        return objectSearchPair;
    }
}
