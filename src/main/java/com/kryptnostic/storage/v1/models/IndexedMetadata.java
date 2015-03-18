package com.kryptnostic.storage.v1.models;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

public class IndexedMetadata {
    private final BitVector             key;
    private final Encryptable<Metadata> data;
    private final String                id;

    @JsonCreator
    public IndexedMetadata(
            @JsonProperty( Names.KEY_FIELD ) BitVector key,
            @JsonProperty( Names.DATA_FIELD ) Encryptable<Metadata> data,
            @JsonProperty( Names.ID_FIELD ) String id ) {
        this.key = key;
        this.data = data;
        this.id = id;
    }

    @JsonProperty( Names.KEY_FIELD )
    public BitVector getKey() {
        return key;
    }

    @JsonProperty( Names.DATA_FIELD )
    public Encryptable<Metadata> getData() {
        return data;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getId() {
        return id;
    }
}
