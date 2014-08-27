package com.kryptnostic.search.v1.client;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.search.v1.models.request.SearchRequest;
import com.kryptnostic.search.v1.models.response.SearchResultResponse;

public interface SearchApi {
    String SEARCH = "/search";

    /**
     * Search on stored documents.
     * 
     * @return SearchResult
     */
    @POST(SEARCH)
    SearchResultResponse search(@Body SearchRequest request);

    /**
     * Search on stored documents.
     *   
     * @return SearchResult
     */
    @POST(SEARCH)
    SearchResultResponse search(@Body List<SearchRequest> requests);
}
