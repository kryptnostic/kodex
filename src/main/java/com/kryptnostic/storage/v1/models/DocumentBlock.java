package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

public class DocumentBlock {
    public static final String FIELD_BLOCK  = "block";
    public static final String FIELD_VERIFY = "verify";
    public static final String FIELD_TOTAL  = "total";
    public static final String FIELD_INDEX  = "index";

    private BlockCiphertext    block;
    private String             verify;
    private int                total;
    private int                index;

    @JsonCreator
    public DocumentBlock(
            @JsonProperty( FIELD_BLOCK ) BlockCiphertext block,
            @JsonProperty( FIELD_VERIFY ) String verify,
            @JsonProperty( FIELD_TOTAL ) int total,
            @JsonProperty( FIELD_INDEX ) int index ) {
        this.block = block;
        this.verify = verify;
        this.total = total;
        this.index = index;
    }

    @JsonProperty( FIELD_BLOCK )
    public BlockCiphertext getBlock() {
        return block;
    }

    @JsonProperty( FIELD_VERIFY )
    public String getVerify() {
        return verify;
    }

    @JsonProperty( FIELD_TOTAL )
    public int getTotal() {
        return total;
    }

    @JsonProperty( FIELD_INDEX )
    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals( Object o ) {
        DocumentBlock d = (DocumentBlock) o;
        return block.equals( d.block ) && verify.equals( d.verify ) && total == d.total && index == ( d.index );
    }
}
