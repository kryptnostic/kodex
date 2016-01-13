package com.kryptnostic.search.v1;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kryptnostic.v2.search.SearchResult;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    Map<String, byte[]> buildTermQuery( List<String> tokens );

    Set<SearchResult> search( String... searchTerms );

    Set<SearchResult> search( List<String> searchTerms );

    Set<SearchResult> submitTermQuery( Map<String, byte[]> request );

}
