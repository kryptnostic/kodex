package com.kryptnostic.storage.v2.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.kryptnostic.storage.v2.models.ObjectMetadataNode;
import com.kryptnostic.v2.constants.Names;

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectListingApi {
    String CONTROLLER     = "/objects";
    String TYPE           = "type";
    String PAGE           = Names.PAGE_FIELD;
    String PAGE_SIZE      = Names.SIZE_FIELD;
    String ID             = Names.ID_FIELD;

    String LATEST_PATH    = "/latest";
    String USER_ID_PATH   = "/{" + ID + "}";
    String TYPE_ID_PATH   = "/{" + TYPE + "}";
    String PAGE_SIZE_PATH = "/{" + PAGE_SIZE + "}";
    String PAGE_PATH      = "/{" + PAGE + "}";

    @GET( CONTROLLER + USER_ID_PATH + PAGE_SIZE_PATH )
    Set<UUID> getLatestUnfinishedPageOfObjectIds(
            @Path( ID ) UUID userId,
            @Path( PAGE_SIZE ) Integer pageSize );

    @GET( CONTROLLER + USER_ID_PATH + TYPE_ID_PATH + PAGE_SIZE_PATH )
    Set<UUID> getLatestUnfinishedPageOfObjectIdsByType(
            @Path( ID ) UUID userId,
            @Path( TYPE ) UUID typeId,
            @Path( PAGE_SIZE ) Integer pageSize );

    /**
     * Retrieves all objects owned by a given a user. This is a slow call / uncached call.
     *
     * @param userId The userId for which to return the list of paged objects.
     * @return The UUID of all objects owned by the user.
     */
    @GET( CONTROLLER + USER_ID_PATH )
    Set<UUID> getAllObjectIds( @Path( ID ) UUID userId );

    @GET( CONTROLLER + USER_ID_PATH + PAGE_SIZE_PATH + PAGE_PATH )
    Set<UUID> getAllObjectIdsPaged(
            @Path( ID ) UUID userId,
            @Path( PAGE ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize );

    @GET( CONTROLLER + USER_ID_PATH + TYPE_ID_PATH )
    Set<UUID> getObjectIdsByType( @Path( ID ) UUID userId, @Path( TYPE ) UUID type );

    @GET( CONTROLLER + USER_ID_PATH + TYPE_ID_PATH + PAGE_SIZE_PATH + PAGE_PATH )
    Set<UUID> getObjectIdsByTypePaged(
            @Path( ID ) UUID userId,
            @Path( TYPE ) UUID typeId,
            @Path( PAGE ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize );

    @POST( CONTROLLER + USER_ID_PATH )
    Map<UUID, ObjectMetadataNode> getObjectMetadataTrees( @Path( ID ) UUID userId, @Body Set<UUID> objectIds );

}
