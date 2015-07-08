package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.EncryptableBlock;

public class AppendObjectRequest {
    private final int              size;
    private final EncryptableBlock block;

    @JsonIgnore
    public AppendObjectRequest() {
        this( -1, null );
    }

    @JsonCreator
    public AppendObjectRequest(
            @JsonProperty( Names.SIZE_FIELD ) int size,
            @JsonProperty( Names.BLOCK_FIELD ) EncryptableBlock block ) {
        this.size = size;
        this.block = block;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size;
    }

    @JsonProperty( Names.BLOCK_FIELD )
    public EncryptableBlock getBlock() {
        return block;
    }

}
