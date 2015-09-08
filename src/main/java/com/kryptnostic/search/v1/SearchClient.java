package com.kryptnostic.search.v1;

import com.kryptnostic.search.v1.models.request.SearchRequest;
import com.kryptnostic.search.v1.models.response.SearchResultResponse;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    public SearchResultResponse search( String query );

    public SearchResultResponse search( String query, SearchRequest request );

    public SearchResultResponse search( SearchRequest request );

}
