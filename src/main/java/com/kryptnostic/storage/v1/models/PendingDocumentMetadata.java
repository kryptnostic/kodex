package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

public class PendingDocumentMetadata extends DocumentMetadata {
    public static final String FIELD_RECEIVED_BLOCKS = "receivedBlocks";
    private int                receivedBlocks;

    @JsonCreator
    public PendingDocumentMetadata(
            @JsonProperty( FIELD_ID ) String id,
            @JsonProperty( FIELD_NUM_BLOCKS ) int numBlocks,
            @JsonProperty( FIELD_VERSION ) int version,
            @JsonProperty( FIELD_RECEIVED_BLOCKS ) Optional<Integer> receivedBlocks ) {
        super( id, numBlocks, version );
        this.receivedBlocks = receivedBlocks.or( 0 );
    }

    @JsonProperty( FIELD_RECEIVED_BLOCKS )
    public int getReceivedBlocks() {
        return receivedBlocks;
    }

    public int increment() {
        return ++receivedBlocks;
    }

}
