package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PendingDocumentMetadata extends DocumentMetadata implements Serializable {

    public static final String FIELD_RECEIVED_BLOCKS = "receivedBlocks";

    private int receivedBlocks;

    @JsonCreator
    public PendingDocumentMetadata(@JsonProperty(FIELD_ID) String id, @JsonProperty(FIELD_NUM_BLOCKS) int numBlocks,
            @JsonProperty(FIELD_VERSION) int version) {
        super(id, numBlocks, version);
        this.receivedBlocks = 0;
    }

    @JsonProperty(FIELD_RECEIVED_BLOCKS)
    public int getReceivedBlocks() {
        return receivedBlocks;
    }

    public int increment() {
        return ++receivedBlocks;
    }

}
