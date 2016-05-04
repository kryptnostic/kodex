package com.kryptnostic.v2.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.constants.Names;

public class IndexSegmentRequest {
    private final byte[]          address;
    private final BlockCiphertext contents;

    @JsonCreator
    public IndexSegmentRequest(
            @JsonProperty( Names.ADDRESS_FIELD ) byte[] address,
            @JsonProperty( Names.CONTENTS ) BlockCiphertext contents ) {
        this.address = address;
        this.contents = contents;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public byte[] getAddress() {
        return address;
    }

    @JsonProperty( Names.CONTENTS )
    public BlockCiphertext getContents() {
        return contents;
    }

}
