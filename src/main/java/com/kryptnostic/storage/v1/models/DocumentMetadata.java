package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public DocumentMetadata(
            @JsonProperty( Names.ID_FIELD ) String id,
            @JsonProperty( Names.VERSION_FIELD ) int version,
            @JsonProperty( Names.BLOCKS_FIELD ) int numBlocks,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy ) {
        this.id = id;
        this.version = version;
        this.numBlocks = numBlocks;
        this.encryptedClassName = encryptedClassName;
        this.chunkingStrategy = chunkingStrategy;
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
    @JsonProperty( Names.BLOCKS_FIELD )
    public int getNumBlocks() {
        return numBlocks;
    }

    @Override
    public boolean equals( Object obj ) {
        DocumentMetadata other = (DocumentMetadata) obj;
        return id.equals( other.id ) && version == other.version;
    }

    @JsonProperty( Names.NAME_FIELD )
    public BlockCiphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonProperty( Names.STRATEGY_FIELD )
    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }
}
