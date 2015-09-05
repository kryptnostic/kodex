package com.kryptnostic.search.v1.http;

import java.util.Set;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.search.v1.models.request.SearchRequest;
import com.kryptnostic.search.v1.models.response.SearchResultResponse;

public interface SearchApi {
    String CONTROLLER = "/search";
    String FAST = "/fast";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    SearchResultResponse search( @Body SearchRequest requests );

    @POST( CONTROLLER + FAST )
    Set<byte[]> lowLevelSearch( @Body byte[] encryptedSearchToken );
}
