package com.kryptnostic.storage.v1.models;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

/**
 * Stores document identifier and document version
 * 
 * @author sinaiman
 */
public class DocumentMetadata {
    private final String           id;
    private final int              version;
    private final int              numBlocks;
    private final BlockCiphertext  encryptedClassName;
    private final ChunkingStrategy chunkingStrategy;

    private final Set<UserKey>     owners;
    private final Set<UserKey>     readers;
    private final Set<UserKey>     writers;

    private final DateTime         createdTime;

    @JsonIgnore
    public DocumentMetadata( String id ) {
        this( id, null, null );
    }

    /**
     * @param id Document identifier
     */
    @JsonIgnore
    public DocumentMetadata( String id, BlockCiphertext encryptedClassName, ChunkingStrategy chunkingStrategy ) {
        this( id, 0, encryptedClassName, chunkingStrategy );
    }

    /**
     * @param id Document identifier
     * @param version 0-based version index
     */
    @JsonIgnore
    public DocumentMetadata(
            String id,
            int version,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy ) {
        this( id, version, 0, encryptedClassName, chunkingStrategy );
    }

    @JsonIgnore
    public DocumentMetadata(
            String id,
            int version,
            int numBlocks,
            BlockCiphertext encryptedClassName,
            ChunkingStrategy chunkingStrategy ) {
        this( id, version, numBlocks, encryptedClassName, chunkingStrategy, Sets.<UserKey> newHashSet(), Sets
                .<UserKey> newHashSet(), Sets.<UserKey> newHashSet() );
    }

    @JsonCreator
    public DocumentMetadata(
            @JsonProperty( Names.ID_FIELD ) String id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( Names.TOTAL_FIELD ) int numBlocks,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy,
            @JsonProperty( Names.OWNERS_FIELD ) Set<UserKey> owners,
            @JsonProperty( Names.READERS_FIELD ) Set<UserKey> readers,
            @JsonProperty( Names.WRITERS_FIELD ) Set<UserKey> writers ) {
        this.id = id;
        this.version = version;
        this.numBlocks = numBlocks;
        this.encryptedClassName = encryptedClassName;
        this.chunkingStrategy = chunkingStrategy;

        this.owners = owners;
        this.readers = readers;
        this.writers = writers;

        this.createdTime = DateTime.now();
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
        DocumentMetadata other = (DocumentMetadata) obj;
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
}
