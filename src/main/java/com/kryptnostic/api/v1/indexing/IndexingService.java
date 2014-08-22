package com.kryptnostic.api.v1.indexing;

import java.util.Set;

import com.kryptnostic.api.v1.indexing.analysis.Analyzer;
import com.kryptnostic.api.v1.indexing.metadata.Metadatum;

public interface IndexingService {
	Set<Metadatum> index( String documentId , String document );
	boolean registerAnalyzer( Analyzer analyzer );
	Set<Analyzer> getAnalyzers();
}
