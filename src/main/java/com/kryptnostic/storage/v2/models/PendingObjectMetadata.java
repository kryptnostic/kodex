package com.kryptnostic.storage.v2.models;

import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

public class PendingObjectMetadata extends ObjectMetadata {
    private int receivedBlocks;

    @JsonCreator
    public PendingObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( Names.TOTAL_FIELD ) int numBlocks,
            @JsonProperty( Names.SIZE_FIELD ) int size,
            @JsonProperty( Names.CHILD_OBJECT_COUNT_FIELD ) int childObjectCount,
            @JsonProperty( Names.USERNAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy,
            @JsonProperty( Names.CREATOR_FIELD ) UUID creator,
            @JsonProperty( Names.OWNERS_FIELD ) Set<UUID> owners,
            @JsonProperty( Names.READERS_FIELD ) Set<UUID> readers,
            @JsonProperty( Names.WRITERS_FIELD ) Set<UUID> writers,
            @JsonProperty( Names.TYPE_FIELD ) String type,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime,
            @JsonProperty( Names.BLOCKS_FIELD ) Optional<Integer> receivedBlocks ) {
        super(
                id,
                version,
                numBlocks,
                size,
                childObjectCount,
                encryptedClassName,
                chunkingStrategy,
                creator,
                owners,
                readers,
                writers,
                type,
                createdTime );
        this.receivedBlocks = receivedBlocks.or( 0 );
    }

    @JsonIgnore
    public PendingObjectMetadata(
            UUID id,
            int version,
            int numBlocks,
            int size,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            UUID creator,
            Optional<Integer> receivedBlocks,
            String type,
            DateTime createdTime ) {
        this(
                id,
                version,
                numBlocks,
                size,
                0,
                encryptedClassName,
                chunkingStrategy,
                creator,
                Sets.<UUID> newHashSet(),
                Sets.<UUID> newHashSet(),
                Sets.<UUID> newHashSet(),
                type,
                createdTime,
                receivedBlocks );
    }

    public PendingObjectMetadata(
            UUID id,
            int version,
            int numBlocks,
            int size,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            UUID creator,
            Set<UUID> owners,
            Optional<Integer> receivedBlocks,
            String type,
            DateTime createdTime ) {
        this(
                id,
                version,
                numBlocks,
                size,
                0,
                encryptedClassName,
                chunkingStrategy,
                creator,
                owners,
                Sets.<UUID> newHashSet(),
                Sets.<UUID> newHashSet(),
                type,
                createdTime,
                receivedBlocks );
    }

    public PendingObjectMetadata(
            UUID newObjectId,
            String type,
            UUID creator,
            Set<UUID> owners,
            // TODO: figure out a way to fix this useless Optional passthrough.
            //      Doing Optional.absent() in this() doesn't get typed correctly
            //      so the compiler complains that it cant find the correct constructor
            Optional<Integer> crap ) {
        this( newObjectId,
                -1,
                -1,
                -1,
                null,
                null,
                creator,
                owners,
                crap,
                type,
                DateTime.now() );
    }

    /**
     * Converts this PendingObjectMetadata to an ObjectMetadata and increments the document version
     * @return
     */
    public ObjectMetadata toNewObjectMetadata() {
        return new ObjectMetadata(
                id,
                version + 1,
                numBlocks,
                size,
                childObjectCount,
                encryptedClassName,
                chunkingStrategy,
                creator,
                owners,
                readers,
                writers,
                type );
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
