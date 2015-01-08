package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Stores document identifier and document version
 * 
 * @author sinaiman
 */
public class DocumentMetadata {
    private final String id;
    private final int    version;

    /**
     * @param id Document identifier
     */
    @JsonIgnore
    public DocumentMetadata( String id ) {
        this( id, 0 );
    }

    /**
     * @param id Document identifier
     * @param version 0-based version index
     */
    @JsonCreator
    public DocumentMetadata( @JsonProperty( Names.ID_FIELD ) String id, @JsonProperty( Names.VERSION_FIELD ) int version ) {
        this.id = id;
        this.version = version;
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

    @Override
    public boolean equals( Object obj ) {
        DocumentMetadata other = (DocumentMetadata) obj;
        return id.equals( other.id ) && version == other.version;
    }
}
