package com.kryptnostic.kodex.v1.indexing;

import java.util.Set;

import com.kryptnostic.kodex.v1.indexing.analysis.Analyzer;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;

public interface Indexer {
    /**
     * Generates Metadatum for a document
     * @param documentId
     * @param document
     * @return
     */
	Set<Metadata> index( String documentId , String document );
	boolean registerAnalyzer( Analyzer analyzer );
	Set<Analyzer> getAnalyzers();
}
