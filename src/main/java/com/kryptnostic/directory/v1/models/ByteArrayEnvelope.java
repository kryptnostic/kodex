package com.kryptnostic.directory.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ByteArrayEnvelope {
    private final byte[] bytes;

    @JsonCreator
    public ByteArrayEnvelope( @JsonProperty( Names.DATA_FIELD ) byte[] bytes ) {
        this.bytes = bytes;
    }

    @JsonProperty( Names.DATA_FIELD )
    public byte[] getBytes() {
        return bytes;
    }
}
