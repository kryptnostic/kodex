package com.kryptnostic.v2.indexing;

import java.util.Set;

import com.kryptnostic.kodex.v1.indexing.analysis.Analyzer;
import com.kryptnostic.v2.indexing.metadata.BucketedMetadata;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public interface Indexer {
    int DEFAULT_BUCKET_SIZE = 10;
    /**
     * Generates Metadatum for an object
     * 
     * @param objectId
     * @param object
     * @return
     */
    Set<BucketedMetadata> index( VersionedObjectKey objectId, String object );

    boolean registerAnalyzer( Analyzer analyzer );

    Set<Analyzer> getAnalyzers();
}
