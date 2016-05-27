package com.kryptnostic.v2.storage.api;

import java.util.UUID;

import com.kryptnostic.v2.storage.models.ObjectTreeLoadRequest;
import com.kryptnostic.v2.storage.models.ObjectTreeLoadResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ObjectTreeApi {
    String CONTROLLER = "/tree";

    @POST( CONTROLLER + "/{objectId}/{version}/{size}" )
    ObjectTreeLoadResponse startPagedInOrderTraversal(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( PAGE_SIZE_VAR ) int scrollSize,
            ObjectTreeLoadRequest request);

    @POST( CONTROLLER + FULL_LEVELS_PREV_PAGE_PATH )
    ObjectTreeLoadResponse forwardPagedInOrderTraversal(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( LAST_OBJECT_ID_VAR ) UUID lastObjectId,
            @Path( LAST_OBJECT_VERSION_VAR ) long lastObjectVersion,
            @Path( PAGE_SIZE_VAR ) int pageSize,
            @Body ObjectTreeLoadRequest request);

    @POST( CONTROLLER + FULL_LEVELS_NEXT_PAGE_PATH )
    ObjectTreeLoadResponse reversePagedInOrderTraversal(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( LAST_OBJECT_ID_VAR ) UUID lastObjectId,
            @Path( LAST_OBJECT_VERSION_VAR ) long lastObjectVersion,
            @Path( PAGE_SIZE_VAR ) int pageSize,
            @Body ObjectTreeLoadRequest request);
}
