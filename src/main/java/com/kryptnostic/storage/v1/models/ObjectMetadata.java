package com.kryptnostic.storage.v1.models;

import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

/**
 * Stores document identifier and document version
 *
 * @author sinaiman, rbuckheit
 */
@Immutable
public class ObjectMetadata {
    @JsonIgnore
    public static final String       DEFAULT_TYPE = "object";
    protected final String           id;
    protected final int              version;
    protected final int              numBlocks;
    protected final int              childObjectCount;
    protected final BlockCiphertext  encryptedClassName;
    protected final ChunkingStrategy chunkingStrategy;

    protected final ImmutableSet<UserKey> owners;
    protected final ImmutableSet<UserKey> readers;
    protected final ImmutableSet<UserKey> writers;

    protected final DateTime         createdTime;

    protected final String           type;


    /**
     * constructs metadata with default values
     * @param id
     */
    @JsonIgnore
    public ObjectMetadata( String id ) {
        this( id, 0, null, null );
    }

    /**
     * @param id Document identifier
     * @param version 0-based version index
     */
    @JsonIgnore
    public ObjectMetadata( String id, int version, BlockCiphertext encryptedClassName, ChunkingStrategy chunkingStrategy ) {
        this( id, version, 0, encryptedClassName, chunkingStrategy );
    }

    @JsonIgnore
    public ObjectMetadata(
            String id,
            int version,
            int numBlocks,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy ) {
        this( id, version, numBlocks, 0, encryptedClassName, chunkingStrategy, Sets.<UserKey> newHashSet(), Sets
                .<UserKey> newHashSet(), Sets.<UserKey> newHashSet(), DEFAULT_TYPE );
    }

    @JsonIgnore
    public ObjectMetadata(
            String id,
            int version,
            int numBlocks,
            int childObjectCount,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy,
            Set<UserKey> owners,
            Set<UserKey> readers,
            Set<UserKey> writers,
            String type ) {
        this( id, version, numBlocks, childObjectCount, encryptedClassName, chunkingStrategy, owners,
                readers, writers, type, DateTime.now() );
    }

    public static ObjectMetadata copyIncrementingChildCount(ObjectMetadata meta){
        return new ObjectMetadata(meta.getId(),
                meta.getVersion(),
                meta.getNumBlocks(),
                meta.getChildObjectCount() + 1,
                meta.getEncryptedClassName(),
                meta.getChunkingStrategy(),
                meta.getOwners(),
                meta.getReaders(),
                meta.getWriters(),
                meta.getType(),
                meta.getCreatedTime());
    }

    @JsonCreator
    public ObjectMetadata(
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
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime) {
        this.id = id;
        this.version = version;
        this.numBlocks = numBlocks;
        this.childObjectCount = childObjectCount;
        this.encryptedClassName = encryptedClassName;
        this.chunkingStrategy = chunkingStrategy;

        this.owners = ImmutableSet.copyOf(owners);
        this.readers = ImmutableSet.copyOf(readers);
        this.writers = ImmutableSet.copyOf(writers);

        this.type = type.toLowerCase();

        this.createdTime = createdTime;
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

    @JsonProperty( Names.NAME_FIELD )
    public BlockCiphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonProperty( Names.STRATEGY_FIELD )
    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }

    @JsonProperty( Names.OWNERS_FIELD )
    public Set<UserKey> getOwners() {
        return owners;
    }

    @JsonProperty( Names.READERS_FIELD )
    public Set<UserKey> getReaders() {
        return readers;
    }

    @JsonProperty( Names.WRITERS_FIELD )
    public Set<UserKey> getWriters() {
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
}
