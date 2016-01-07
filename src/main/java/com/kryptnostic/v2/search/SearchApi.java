package com.kryptnostic.v2.search;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.POST;

public interface SearchApi {
    String CONTROLLER = "/search";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    SearchResultResponse submitTermQuery( @Body Map<String,byte[]> query );
}
