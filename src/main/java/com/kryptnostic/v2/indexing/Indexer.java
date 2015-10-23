package com.kryptnostic.v2.indexing;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.kodex.v1.indexing.analysis.Analyzer;
import com.kryptnostic.v2.indexing.metadata.Metadata;

public interface Indexer {
    /**
     * Generates Metadatum for an object
     * 
     * @param objectId
     * @param object
     * @return
     */
    Set<Metadata> index( UUID objectId, String object );

    boolean registerAnalyzer( Analyzer analyzer );

    Set<Analyzer> getAnalyzers();
}
