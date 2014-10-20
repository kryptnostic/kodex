package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Document implements Serializable {
    public static final String FIELD_METADATA = "metadata";
    public static final String FIELD_BLOCKS = "blocks";

    private final DocumentMetadata metadata;
    private final DocumentBlocks blocks;

    @JsonCreator
    public Document(@JsonProperty(FIELD_METADATA) DocumentMetadata metadata,
            @JsonProperty(FIELD_BLOCKS) DocumentBlocks blocks) {
        this.metadata = metadata;
        this.blocks = blocks;
    }

    @JsonProperty(FIELD_BLOCKS)
    public DocumentBlocks getBlocks() {
        return blocks;
    }

    @JsonProperty(FIELD_METADATA)
    public DocumentMetadata getMetadata() {
        return metadata;
    }

    /**
     * This does not check for block content equality because blocks are encrypted This uses the verification hash,
     * which is not guaranteed to be consistent with the document blocks
     */
    @Override
    public boolean equals(Object o) {
        Document d = (Document) o;
        return blocks.size() == d.blocks.size() && metadata.equals(d.metadata);
    }
}
