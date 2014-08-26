package com.kryptnostic.api.v1.client;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.api.v1.models.SearchResult;
import com.kryptnostic.api.v1.models.request.SearchRequest;

public interface SearchAPI {
    String SEARCH = "/search";

    /**
     * Search on stored documents.
     * 
     * @return SearchResult
     */
    @POST(SEARCH)
    SearchResult search(@Body SearchRequest request );
}
