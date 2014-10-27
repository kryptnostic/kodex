package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentMetadata {
    public static final String FIELD_ID         = "id";
    public static final String FIELD_VERSION    = "version";
    public static final String FIELD_NUM_BLOCKS = "numBlocks";

    private final String       id;
    private final int          version;
    private final int          numBlocks;

    @JsonIgnore
    public DocumentMetadata( String id, int numBlocks ) {
        this.id = id;
        this.version = 0;
        this.numBlocks = numBlocks;
    }

    @JsonCreator
    public DocumentMetadata(
            @JsonProperty( FIELD_ID ) String id,
            @JsonProperty( FIELD_NUM_BLOCKS ) int numBlocks,
            @JsonProperty( FIELD_VERSION ) int version ) {
        this.id = id;
        this.version = version;
        this.numBlocks = numBlocks;
    }

    @JsonProperty( FIELD_ID )
    public String getId() {
        return id;
    }

    @JsonProperty( FIELD_VERSION )
    public int getVersion() {
        return version;
    }

    @JsonProperty( FIELD_NUM_BLOCKS )
    public int getNumBlocks() {
        return numBlocks;
    }

    @Override
    public boolean equals( Object obj ) {
        DocumentMetadata other = (DocumentMetadata) obj;
        return id.equals( other.id ) && version == other.version;
    }
}
