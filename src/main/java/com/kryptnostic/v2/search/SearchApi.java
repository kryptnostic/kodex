package com.kryptnostic.v2.search;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface SearchApi {
    String CONTROLLER     = "/search";

    String OBJECT_ID      = "objectId";
    String OBJECT_ID_PATH = "/{" + OBJECT_ID + "}";
    String VERSION        = "version";
    String VERSION_PATH   = "/{" + VERSION + "}";

    /**
     * Search on stored documents.
     *
     * @return SearchResult
     */
    @POST( CONTROLLER )
    Set<SearchResult> submitTermQuery( @Body Map<String, byte[]> query );

    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH )
    public Integer getTotalSegments( @Path( OBJECT_ID ) UUID objectId, @Path( VERSION ) long version);
}
