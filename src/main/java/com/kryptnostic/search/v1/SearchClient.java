package com.kryptnostic.search.v1;

import java.util.List;
import java.util.Map;

import com.kryptnostic.v2.search.SearchResultResponse;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    Map<String, byte[]> buildTermQuery( List<String> tokens );

    SearchResultResponse search( String... searchTerms );

    SearchResultResponse search( List<String> searchTerms );

    SearchResultResponse submitTermQuery( Map<String, byte[]> request );

}
