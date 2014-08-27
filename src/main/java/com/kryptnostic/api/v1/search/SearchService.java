package com.kryptnostic.api.v1.search;

import java.util.Set;

import com.kryptnostic.api.v1.indexing.metadata.Metadatum;

public interface SearchService {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set<Metadatum>, a collection of Metadatum associating the query with documents.
     */
    public Set<Metadatum> search(String query);
}
