package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.List;
import java.util.Map;

import cern.colt.bitvector.BitVector;

public interface Metadata {
    Map<BitVector, List<Metadatum>> getMetadataMap();
    public List<BitVector> getNonces();
}
