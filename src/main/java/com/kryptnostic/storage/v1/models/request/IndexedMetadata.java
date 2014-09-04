package com.kryptnostic.storage.v1.models.request;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.Encryptable;

public class IndexedMetadata {
    private static final String KEY = "key";
    private static final String DATA = "data";

    private final BitVector key;
    private final Encryptable<?> data;

    @JsonCreator
    public IndexedMetadata(@JsonProperty(KEY) BitVector key, @JsonProperty(DATA) Encryptable<?> data) {
        super();
        this.key = key;
        this.data = data;
    }

    @JsonProperty(KEY)
    public BitVector getKey() {
        return key;
    }

    @JsonProperty(DATA)
    public Encryptable<?> getData() {
        return data;
    }
}
