package com.kryptnostic.indexing.v1;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class ObjectSearchPair {
    public static final String SEARCH_PAIR_FIELD = "searchPair";
    private final byte[]       objectSearchPair;

    @JsonCreator
    public ObjectSearchPair( @JsonProperty( SEARCH_PAIR_FIELD ) byte[] objectSearchPair ) {
        Preconditions.checkState( objectSearchPair.length == 2080, "Search pair must be 2080 bytes long." );
        this.objectSearchPair = objectSearchPair;
    }

    @JsonProperty( SEARCH_PAIR_FIELD )
    public byte[] getSearchPair() {
        return objectSearchPair;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( objectSearchPair );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        ObjectSearchPair other = (ObjectSearchPair) obj;
        if ( !Arrays.equals( objectSearchPair, other.objectSearchPair ) ) return false;
        return true;
    }
}
