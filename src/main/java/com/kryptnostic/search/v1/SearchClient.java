package com.kryptnostic.search.v1;

import java.util.List;

import com.kryptnostic.v2.search.SearchResultResponse;
import com.kryptnostic.v2.search.TermQuery;

public interface SearchClient {
    /**
     * Submit search query on stored documents.
     * 
     * @return Set &lt;Metadatum&gt; a collection of Metadatum associating the query with documents.
     */
    TermQuery buildTermQuery( List<String> tokens );

    SearchResultResponse search( String... searchTerms );

    SearchResultResponse search( List<String> searchTerms );

    SearchResultResponse submitTermQuery( TermQuery request );

}
