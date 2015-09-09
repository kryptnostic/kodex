package com.kryptnostic.indexing.v1;

import java.util.List;

import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;

public class PaddedMetadata {
    private final byte[]         address;
    private final List<Metadata> metadata;

    public PaddedMetadata( byte[] address, List<Metadata> metadata ) {
        this.address = address;
        this.metadata = metadata;
    }

    public byte[] getAddress() {
        return address;
    }

    public List<Metadata> getMetadata() {
        return metadata;
    }

    public static PaddedMetadata fromAddressAndMetadata( byte[] address, List<Metadata> metadata ) {
        return new PaddedMetadata( address, metadata );
    }
}
