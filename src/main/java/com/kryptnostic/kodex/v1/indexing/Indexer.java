package com.kryptnostic.kodex.v1.indexing;

import java.util.Set;

import com.kryptnostic.kodex.v1.indexing.analysis.Analyzer;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;

public interface Indexer {
    /**
     * Generates Metadatum for an object
     * 
     * @param objectId
     * @param object
     * @return
     */
    Set<Metadata> index( String objectId, String object );

    boolean registerAnalyzer( Analyzer analyzer );

    Set<Analyzer> getAnalyzers();
}
