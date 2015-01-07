package com.kryptnostic.storage.v1.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

public class EncryptableBlocks implements Serializable {
    private static final long   serialVersionUID = -7306228218357504656L;

    private static final String FIELD_BLOCKS     = "blocks";

    private List<EncryptableBlock> blocks;

    public EncryptableBlocks() {
        this.blocks = Lists.newArrayList();
    }

    @JsonCreator
    public EncryptableBlocks( @JsonProperty( FIELD_BLOCKS ) List<EncryptableBlock> blocks ) {
        this.blocks = blocks;
    }

    public List<EncryptableBlock> getBlocks() {
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
    public boolean equals( Object o ) {
        EncryptableBlocks other = (EncryptableBlocks) o;
        return size() == other.size();
    }

    public static boolean isValid( EncryptableBlock block ) {
        return Arrays.equals(
                Encryptable.hashFunction.hashBytes( block.getBlock().getContents() ).asBytes(),
                block.getVerify() );
    }
}
