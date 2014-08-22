package com.kryptnostic.api.v1.search;

import java.util.Set;

import com.kryptnostic.api.v1.indexing.metadata.Metadatum;

public interface SearchService {
	public Set<Metadatum> search( String query );
}
