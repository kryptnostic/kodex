package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.List;
import java.util.Map;

import cern.colt.bitvector.BitVector;

public class MappedMetadata {
    private final Map<BitVector, List<Metadata>> metadataMap;

    public MappedMetadata( Map<BitVector, List<Metadata>> metadataMap ) {
        this.metadataMap = metadataMap;
    }

    public Map<BitVector, List<Metadata>> getMetadataMap() {
        return metadataMap;
    }

    public static MappedMetadata from( Map<BitVector, List<Metadata>> metadataMap ) {
        return new MappedMetadata( metadataMap );
    }

}
