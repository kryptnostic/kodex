package com.kryptnostic.storage.v1.models;

import java.util.Set;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.principal.UserKey;
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
            @JsonProperty( Names.CHILD_OBJECT_COUNT_FIELD ) int childObjectCount,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy,
            @JsonProperty( Names.OWNERS_FIELD ) Set<UserKey> owners,
            @JsonProperty( Names.READERS_FIELD ) Set<UserKey> readers,
            @JsonProperty( Names.WRITERS_FIELD ) Set<UserKey> writers,
            @JsonProperty( Names.TYPE_FIELD ) String type,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime,
            @JsonProperty( Names.BLOCKS_FIELD ) Optional<Integer> receivedBlocks ) {
        super(
                id,
                version,
                numBlocks,
                childObjectCount,
                encryptedClassName,
                chunkingStrategy,
                owners,
                readers,
                writers,
                type,
                createdTime );
        this.receivedBlocks = receivedBlocks.or( 0 );
    }

    @JsonIgnore
    public PendingObjectMetadata(
            String id,
            int version,
            int numBlocks,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            Optional<Integer> receivedBlocks,
            String type,
            DateTime createdTime ) {
        this( id, version, numBlocks, 0, encryptedClassName, chunkingStrategy, Sets.<UserKey> newHashSet(), Sets
                .<UserKey> newHashSet(), Sets.<UserKey> newHashSet(), type, createdTime, receivedBlocks );
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

    @JsonIgnore
    public int increment() {
        return ++receivedBlocks;
    }

}
