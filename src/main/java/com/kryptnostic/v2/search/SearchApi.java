package com.kryptnostic.v2.search;

import java.util.Map;
import java.util.SortedSet;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface SearchApi {
    String CONTROLLER = "/search";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    SortedSet<SearchResult> submitTermQuery( @Body Map<String, byte[]> query );
    
    @GET( CONTROLLER + "/object/{id}/{version}" )
    public Integer getTotalSegments();  
}
