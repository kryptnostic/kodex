package com.kryptnostic.storage.v1.models.request;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;

public class IndexableMetadata {
    private static final String KEY = "key";
    private static final String DATA = "data";

    private final BitVector key;
    private final Metadatum data;

    @JsonCreator
    public IndexableMetadata(@JsonProperty(KEY) BitVector key, @JsonProperty(DATA) Metadatum data) {
        super();
        this.key = key;
        this.data = data;
    }

    @JsonProperty(KEY)
    public BitVector getKey() {
        return key;
    }

    @JsonProperty(DATA)
    public Metadatum getData() {
        return data;
    }
}
