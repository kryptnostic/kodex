package com.kryptnostic.kodex.v1.indexing.metadata;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public class MappedMetadata {
    private final Map<ByteBuffer, List<Metadata>> metadataMap;

    public MappedMetadata( Map<ByteBuffer, List<Metadata>> metadataMap ) {
        this.metadataMap = metadataMap;
    }

    public Map<ByteBuffer, List<Metadata>> getMetadataMap() {
        return metadataMap;
    }

    public static MappedMetadata from( Map<ByteBuffer, List<Metadata>> metadataMap ) {
        return new MappedMetadata( metadataMap );
    }

}
