package com.kryptnostic.storage.v2.http;

import java.util.Set;
import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.POST;
import retrofit.http.Path;

import com.kryptnostic.storage.v2.models.PaddedMetadataObjectIds;

public interface MetadataStorageApi {
    String CONTROLLER     = "/metadata";
    String OBJECT_ID      = "objectId";
    String DELETE         = "/delete";

    String OBJECT_ID_PATH = "/{" + OBJECT_ID + "}";

    @POST( CONTROLLER + OBJECT_ID_PATH )
    Response createMetadataEntry( @Path( OBJECT_ID ) UUID objectId, Set<PaddedMetadataObjectIds> paddedMetadataObjectIds );

    @DELETE( CONTROLLER + OBJECT_ID_PATH )
    Response createMetadataEntry( @Path( OBJECT_ID ) UUID objectId );
}
