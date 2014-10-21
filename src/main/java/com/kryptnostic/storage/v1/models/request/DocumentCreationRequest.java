package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentCreationRequest {
    private static final String FIELD_VERIFICATION = "verification";
    private static final String FIELD_NUM_BLOCKS = "numBlocks";
    private final String verificationHash;
    private final int numBlocks;

    @JsonCreator
    public DocumentCreationRequest(@JsonProperty(FIELD_VERIFICATION) String verificationHash,
            @JsonProperty(FIELD_NUM_BLOCKS) int numBlocks) {
        this.verificationHash = verificationHash;
        this.numBlocks = numBlocks;
    }

    @JsonProperty(FIELD_VERIFICATION)
    public String getVerificationHash() {
        return verificationHash;
    }

    @JsonProperty(FIELD_NUM_BLOCKS)
    public int getNumBlocks() {
        return numBlocks;
    }
}
