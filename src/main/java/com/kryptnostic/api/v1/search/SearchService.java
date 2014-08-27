package com.kryptnostic.api.v1.search;

import java.util.Set;

import com.kryptnostic.api.v1.indexing.metadata.Metadatum;

public interface SearchService {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt;
     */
    public Set<Metadatum> search(String query);
}
