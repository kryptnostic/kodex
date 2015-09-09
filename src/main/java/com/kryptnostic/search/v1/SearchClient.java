package com.kryptnostic.search.v1;

import java.util.List;

import com.kryptnostic.search.v1.models.request.SearchRequest;
import com.kryptnostic.search.v1.models.response.SearchResultResponse;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    SearchResultResponse search( String query );

    SearchResultResponse search( String query, SearchRequest request );

    SearchResultResponse search( SearchRequest request );

    SearchRequest generateSearchRequest( List<String> tokens );

}
