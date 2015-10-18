package com.kryptnostic.storage.v2.models;

import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class PendingObjectMetadata extends ObjectMetadata {
    private int receivedBlocks;

    @JsonCreator
    public PendingObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) UUID id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( Names.TOTAL_FIELD ) int numBlocks,
            @JsonProperty( Names.SIZE_FIELD ) int size,
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.CREATOR_FIELD ) UUID creator,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime,
            @JsonProperty( Names.BLOCKS_FIELD ) Optional<Integer> receivedBlocks ) {
        super(
                id,
                version,
                numBlocks,
                size,
                creator,
                type,
                createdTime );
        this.receivedBlocks = receivedBlocks.or( 0 );
    }

    public PendingObjectMetadata(
            UUID id,
            int version,
            int numBlocks,
            int size,
            UUID type,
            UUID creator,
            Optional<Integer> receivedBlocks,
            DateTime createdTime ) {
        this(
                id,
                version,
                numBlocks,
                size,
                creator,
                type,
                createdTime,
                receivedBlocks );
    }

    public PendingObjectMetadata(
            UUID newObjectId,
            UUID creator,
            UUID type,
            Set<UUID> owners) {
        this( newObjectId,
                -1,
                -1,
                -1,
                creator,
                type,
                Optional.<Integer>absent(),
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
                creator,
                type,
                DateTime.now() );
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
