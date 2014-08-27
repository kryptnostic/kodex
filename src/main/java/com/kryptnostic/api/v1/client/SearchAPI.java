package com.kryptnostic.api.v1.client;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.api.v1.models.SearchResult;
import com.kryptnostic.api.v1.models.request.SearchRequest;

public interface SearchAPI {
    String SEARCH = "/search";

    /**
     * Search on stored documents.
     * 
     * @param SearchRequest request.
     * @return SearchResult
     */
    @POST(SEARCH)
    SearchResult search(@Body SearchRequest request);

    /**
     * Search on stored documents.
     * 
     * @param List<SearchRequest> requests.
     * @return SearchResult
     */
    @POST(SEARCH)
    SearchResult search(@Body List<SearchRequest> requests);
}
