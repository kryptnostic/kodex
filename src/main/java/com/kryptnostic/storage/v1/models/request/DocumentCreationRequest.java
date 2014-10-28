package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentCreationRequest {
    private static final String FIELD_NUM_BLOCKS = "numBlocks";
    private final int numBlocks;

    @JsonCreator
    public DocumentCreationRequest(@JsonProperty(FIELD_NUM_BLOCKS) int numBlocks) {
        this.numBlocks = numBlocks;
    }

    @JsonProperty(FIELD_NUM_BLOCKS)
    public int getNumBlocks() {
        return numBlocks;
    }
}
