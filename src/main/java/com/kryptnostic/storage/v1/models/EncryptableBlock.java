package com.kryptnostic.storage.v1.models;

import java.io.Serializable;
import java.util.Arrays;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

public class EncryptableBlock implements Serializable {
    private final BlockCiphertext  block;
    private final byte[]           verify;
    private final int              index;
    private final boolean          isLast;
    private final BlockCiphertext  encryptedClassName;
    private final ChunkingStrategy chunkingStrategy;
    private DateTime               creationTime;

    @JsonCreator
    public EncryptableBlock(
            @JsonProperty( Names.BLOCK_FIELD ) BlockCiphertext block,
            @JsonProperty( Names.VERIFY_FIELD ) byte[] verify,
            @JsonProperty( Names.INDEX_FIELD ) int index,
            @JsonProperty( Names.LAST_FIELD ) boolean isLast,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext encryptedClassName,
            @JsonProperty( Names.STRATEGY_FIELD ) ChunkingStrategy chunkingStrategy,
            @JsonProperty( Names.CREATED_TIME ) DateTime createdTime ) {
        this.block = block;
        this.verify = verify;
        this.index = index;
        this.isLast = isLast;
        this.encryptedClassName = encryptedClassName;
        this.chunkingStrategy = chunkingStrategy;
        this.creationTime = createdTime;
    }

    @JsonProperty( Names.BLOCK_FIELD )
    public BlockCiphertext getBlock() {
        return block;
    }

    @JsonProperty( Names.VERIFY_FIELD )
    public byte[] getVerify() {
        return verify;
    }

    @JsonProperty( Names.INDEX_FIELD )
    public int getIndex() {
        return index;
    }

    @JsonProperty( Names.LAST_FIELD )
    public boolean isLast() {
        return isLast;
    }

    @JsonProperty( Names.NAME_FIELD )
    public BlockCiphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonProperty( Names.STRATEGY_FIELD )
    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }

    @JsonProperty( Names.CREATED_TIME )
    public DateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals( Object o ) {
        EncryptableBlock d = (EncryptableBlock) o;
        return block.equals( d.block ) && Arrays.equals( verify, d.verify ) && isLast == d.isLast
                && index == ( d.index );
    }
}
