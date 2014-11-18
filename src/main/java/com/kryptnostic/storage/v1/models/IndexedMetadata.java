package com.kryptnostic.storage.v1.models;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.sharing.v1.models.DocumentId;

public class IndexedMetadata {
    private static final String         KEY  = "key";
    private static final String         DATA = "data";
    private static final String         ID   = "id";

    private final BitVector             key;
    private final Encryptable<Metadata> data;
    private final DocumentId            id;

    @JsonCreator
    public IndexedMetadata(
            @JsonProperty( KEY ) BitVector key,
            @JsonProperty( DATA ) Encryptable<Metadata> data,
            @JsonProperty( ID ) DocumentId id ) {
        this.key = key;
        this.data = data;
        this.id = id;
    }

    @JsonProperty( KEY )
    public BitVector getKey() {
        return key;
    }

    @JsonProperty( DATA )
    public Encryptable<Metadata> getData() {
        return data;
    }

    @JsonProperty( ID )
    public DocumentId getId() {
        return id;
    }
}
