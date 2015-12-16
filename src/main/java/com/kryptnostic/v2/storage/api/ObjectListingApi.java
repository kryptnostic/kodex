package com.kryptnostic.v2.storage.api;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.constants.Names;

import retrofit.http.GET;
import retrofit.http.Path;

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
    String TYPE_ID_PATH   = "/type/{" + TYPE + "}";
    String TYPE_NAME_PATH = "/typename/{" + TYPE + "}";
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

    @GET( CONTROLLER + TYPE_NAME_PATH )
    UUID getTypeForName( @Path( TYPE ) String typeName);
}
