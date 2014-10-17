package com.kryptnostic.storage.v1.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {

    public static final String FIELD_ID = "id";
    public static final String FIELD_BLOCKS = "blocks";
    public static final String FIELD_VERIFY = "verify";

    private final String id;
    private final Collection<DocumentBlock> blocks;
    private final String verify;

    @JsonCreator
    public Document(@JsonProperty(FIELD_ID) String id, @JsonProperty(FIELD_BLOCKS) Collection<DocumentBlock> blocks,
            @JsonProperty(FIELD_VERIFY) String verify) {
        this.id = id;
        this.blocks = blocks;
        this.verify = verify;
    }

    @JsonProperty(FIELD_ID)
    public String getId() {
        return id;
    }

    @JsonProperty(FIELD_BLOCKS)
    public Collection<DocumentBlock> getBlocks() {
        return blocks;
    }

    /**
     * So verify
     * 
     * @return Verification hash of all blocks
     */
    @JsonProperty(FIELD_VERIFY)
    public String getVerify() {
        return verify;
    }

    /**
     * This does not check for block content equality because blocks are encrypted
     * This uses the verification hash, which is not guaranteed to be consistent with the document blocks
     */
    @Override
    public boolean equals(Object o) {
        Document d = (Document) o;
        return id.equals(d.id) && blocks.size() == d.blocks.size() && verify.equals(d.verify);
    }
}
