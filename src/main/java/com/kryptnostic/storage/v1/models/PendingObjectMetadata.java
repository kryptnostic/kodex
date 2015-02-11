package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

public class PendingObjectMetadata extends ObjectMetadata {
    private int receivedBlocks;

    @JsonCreator
    public PendingObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) String id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( Names.TOTAL_FIELD ) int numBlocks,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy,
            @JsonProperty( Names.BLOCKS_FIELD ) Optional<Integer> receivedBlocks ) {
        super( id, version, numBlocks, encryptedClassName, chunkingStrategy );
        this.receivedBlocks = receivedBlocks.or( 0 );
    }

    @JsonProperty( Names.BLOCKS_FIELD )
    public int getReceivedBlocks() {
        return receivedBlocks;
    }

    @Override
    public boolean equals( Object obj ) {
        PendingObjectMetadata other = (PendingObjectMetadata) obj;
        return super.equals( other ) && receivedBlocks == other.receivedBlocks;
    }

    public int increment() {
        return ++receivedBlocks;
    }

}
