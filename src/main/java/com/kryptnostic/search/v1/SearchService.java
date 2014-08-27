package com.kryptnostic.search.v1;

import java.util.Set;

import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;

public interface SearchService {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    public Set<Metadatum> search(String query);
}
