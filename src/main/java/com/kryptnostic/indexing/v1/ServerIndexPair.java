package com.kryptnostic.indexing.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;

public class ServerIndexPair {
    public static final String INDEX_PAIR_FIELD = "indexPair";
    private final byte[]       indexPair;

    @JsonCreator
    public ServerIndexPair( @JsonProperty( INDEX_PAIR_FIELD ) byte[] indexPair ) {
        Preconditions.checkState( indexPair.length == 2080, "Index pair must be 2080 bytes long." );
        this.indexPair = indexPair;
    }

    @JsonProperty( INDEX_PAIR_FIELD )
    public byte[] getIndexPair() {
        return indexPair;
    }
}
