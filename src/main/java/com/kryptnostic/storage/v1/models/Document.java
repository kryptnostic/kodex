package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;

public class Document implements Serializable {
    private static final long serialVersionUID = -8243618155514369238L;
    public static final String FIELD_METADATA = "metadata";
    public static final String FIELD_BLOCKS = "blocks";

    private final DocumentMetadata metadata;
    private final DocumentBlock[] blocks;

    @JsonCreator
    public Document(@JsonProperty(FIELD_METADATA) DocumentMetadata metadata,
            @JsonProperty(FIELD_BLOCKS) DocumentBlock[] blocks) {
        this.metadata = metadata;
        this.blocks = blocks;
    }

    @JsonProperty(FIELD_BLOCKS)
    public DocumentBlock[] getBlocks() {
        return blocks;
    }

    @JsonProperty(FIELD_METADATA)
    public DocumentMetadata getMetadata() {
        return metadata;
    }

    /**
     * @return The decrypted document text
     */
    @JsonIgnore
    public String getBody(Kodex<String> kodex) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * This does not check for block content equality because blocks are encrypted This uses the verification hash,
     * which is not guaranteed to be consistent with the document blocks
     */
    @Override
    public boolean equals(Object o) {
        Document d = (Document) o;
        return ( blocks.length == d.blocks.length ) && metadata.equals(d.metadata);
    }
}
