package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class PendingDocumentMetadata extends DocumentMetadata {
    public static final String FIELD_RECEIVED_BLOCKS = "receivedBlocks";
    private int                receivedBlocks;

    @JsonCreator
    public PendingDocumentMetadata(
            @JsonProperty( Names.ID_FIELD ) String id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( FIELD_RECEIVED_BLOCKS ) Optional<Integer> receivedBlocks ) {
        super( id, version );
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
