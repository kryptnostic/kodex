package com.kryptnostic.storage.v2.models;

import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
@Immutable
public class ObjectMetadata {
    protected final UUID             id;
    protected final UUID             type;
    protected final int              version;
    protected final int              numBlocks;

    protected final UUID             creator;
    protected final DateTime         createdTime;
    protected final int              size;

    /**
     * constructs metadata with default values
     *
     * @param id
     */
    @JsonIgnore
    public ObjectMetadata( String id, UUID creator ) {
        this( id, 0, null, null, creator );
    }

    /**
     * @param id Document identifier
     * @param version 0-based version index
     */
    @JsonIgnore
    public ObjectMetadata(
            String id,
            int version,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            UUID creator ) {
        this( id, version, 0, encryptedClassName, chunkingStrategy, creator );
    }

    @JsonIgnore
    public ObjectMetadata(
            String id,
            int version,
            int numBlocks,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            UUID creator ) {
        this(
                id,
                version,
                numBlocks,
                0,
                0,
                encryptedClassName,
                chunkingStrategy,
                creator,
                Sets.<UUID> newHashSet(),
                Sets.<UUID> newHashSet(),
                Sets.<UUID> newHashSet(),
                DEFAULT_TYPE );
    }

    @JsonIgnore
    public ObjectMetadata(
            String id,
            int version,
            int numBlocks,
            int size,
            int childObjectCount,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            UUID creator,
            Set<UUID> owners,
            Set<UUID> readers,
            Set<UUID> writers,
            String type ) {
        this(
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
                DateTime.now() );
    }

    public static ObjectMetadata copyIncrementingChildCount( ObjectMetadata meta ) {
        return new ObjectMetadata(
                meta.getId(),
                meta.getVersion(),
                meta.getNumBlocks(),
                meta.getSize(),
                meta.getChildObjectCount() + 1,
                meta.getEncryptedClassName(),
                meta.getChunkingStrategy(),
                meta.getCreator(),
                meta.getOwners(),
                meta.getReaders(),
                meta.getWriters(),
                meta.getType(),
                meta.getCreatedTime() );
    }

    @JsonCreator
    public ObjectMetadata(
            @JsonProperty( Names.ID_FIELD ) String id,
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
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime ) {
        this.id = id;
        this.version = version;
        this.numBlocks = numBlocks;
        this.childObjectCount = childObjectCount;
        this.encryptedClassName = encryptedClassName;
        this.chunkingStrategy = chunkingStrategy;

        this.creator = creator;
        this.owners = ImmutableSet.copyOf( owners );
        this.readers = ImmutableSet.copyOf( readers );
        this.writers = ImmutableSet.copyOf( writers );

        this.type = type.toLowerCase();

        this.createdTime = createdTime;
        this.size = size;
    }

    /**
     * @return Document identifier
     */
    @JsonProperty( Names.ID_FIELD )
    public String getId() {
        return id;
    }

    /**
     * @return Version of document
     */
    @JsonProperty( Names.VERSION_FIELD )
    public int getVersion() {
        return version;
    }

    /**
     * @return Number of blocks associated with the document
     */
    @JsonProperty( Names.TOTAL_FIELD )
    public int getNumBlocks() {
        return numBlocks;
    }

    @Override
    public boolean equals( Object obj ) {
        ObjectMetadata other = (ObjectMetadata) obj;
        return id.equals( other.id ) && version == other.version && numBlocks == other.numBlocks
                && CollectionUtils.isEqualCollection( owners, other.owners )
                && CollectionUtils.isEqualCollection( writers, other.writers )
                && CollectionUtils.isEqualCollection( readers, other.readers );
    }

    @JsonProperty( Names.USERNAME_FIELD )
    public BlockCiphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonProperty( Names.STRATEGY_FIELD )
    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }

    @JsonProperty( Names.CREATOR_FIELD )
    public UUID getCreator() {
        return creator;
    }

    @JsonProperty( Names.OWNERS_FIELD )
    public Set<UUID> getOwners() {
        return owners;
    }

    @JsonProperty( Names.READERS_FIELD )
    public Set<UUID> getReaders() {
        return readers;
    }

    @JsonProperty( Names.WRITERS_FIELD )
    public Set<UUID> getWriters() {
        return writers;
    }

    @JsonProperty( Names.CREATED_TIME )
    public DateTime getCreatedTime() {
        return createdTime;
    }

    @JsonProperty( Names.TYPE_FIELD )
    public String getType() {
        return type;
    }

    @JsonProperty( Names.CHILD_OBJECT_COUNT_FIELD )
    public int getChildObjectCount() {
        return childObjectCount;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size;
    }
}
