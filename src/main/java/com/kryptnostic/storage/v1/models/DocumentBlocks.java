package com.kryptnostic.storage.v1.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

public class DocumentBlocks implements Serializable {
    private static final long serialVersionUID = -7306228218357504656L;

    private static final String FIELD_BLOCKS = "blocks";

    private List<DocumentBlock> blocks;

    public DocumentBlocks() {
        this.blocks = Lists.newArrayList();
    }

    @JsonCreator
    public DocumentBlocks(@JsonProperty(FIELD_BLOCKS) List<DocumentBlock> blocks) {
        this.blocks = blocks;
    }

    
    public List<DocumentBlock> getBlocks() {
        return blocks;
    }

    public int size() {
        return blocks.size();
    }

    /**
     * This does not check for block content equality because blocks are encrypted This uses the verification hash,
     * which is not guaranteed to be consistent with the document blocks
     */
    @Override
    public boolean equals(Object o) {
        DocumentBlocks other = (DocumentBlocks) o;
        return size() == other.size();
    }
}
