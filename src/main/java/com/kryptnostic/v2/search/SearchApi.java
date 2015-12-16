package com.kryptnostic.v2.search;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.search.v1.models.request.SearchRequest;

public interface SearchApi {
    String CONTROLLER = "/search";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    SearchResultResponse search( @Body SearchRequest requests );

}
