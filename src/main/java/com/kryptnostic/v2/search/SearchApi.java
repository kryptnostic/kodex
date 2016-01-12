package com.kryptnostic.v2.search;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SearchApi {
    String CONTROLLER = "/search";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    Set<SearchResult> submitTermQuery( @Body Map<String, byte[]> query );

    @GET( CONTROLLER + "/object/{id}/{version}" )
    public Integer getTotalSegments( @Path( "id" ) UUID objectId, @Path( "version" ) long version);
}
