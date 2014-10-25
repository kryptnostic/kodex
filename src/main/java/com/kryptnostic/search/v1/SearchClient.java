package com.kryptnostic.search.v1;

import java.util.Collection;

import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.search.v1.models.SearchResult;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     * @throws IrisException
     */
    public Collection<SearchResult> search( String query ) throws IrisException;
}
