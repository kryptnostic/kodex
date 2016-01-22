package com.kryptnostic.v2.storage.api;

import java.util.Set;
import java.util.UUID;

import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * This API is used for retrieving paged lists of objects for a user. Ordering is not guaranteed across calls.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectListingApi {
    String CONTROLLER     = "/listing";
    String OBJECTS        = "/objects";
    String VERSIONED      = "/versioned";
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

    /**
     * Retrieves all objects owned by a given a user. This is a slow call / uncached call.
     *
     * @param userId The userId for which to return the list of paged objects.
     * @return The UUID of all objects owned by the user.
     */
    @GET( CONTROLLER + OBJECTS + USER_ID_PATH )
    Set<UUID> getAllObjectIds( @Path( ID ) UUID userId);

    @GET( CONTROLLER + OBJECTS + USER_ID_PATH + PAGE_SIZE_PATH + PAGE_PATH )
    Set<UUID> getAllObjectIdsPaged(
            @Path( ID ) UUID userId,
            @Path( PAGE ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize);

    @GET( CONTROLLER + VERSIONED + OBJECTS + USER_ID_PATH + TYPE_ID_PATH )
    Set<VersionedObjectKey> getVersionedObjectKeysByType( @Path( ID ) UUID userId, @Path( TYPE ) UUID type);

    @GET( CONTROLLER + OBJECTS + USER_ID_PATH + TYPE_ID_PATH )
    Iterable<UUID> getObjectIdsByType( @Path( ID ) UUID userId, @Path( TYPE ) UUID type);

    @GET( CONTROLLER + OBJECTS + USER_ID_PATH + TYPE_ID_PATH + PAGE_SIZE_PATH + PAGE_PATH )
    Set<UUID> getObjectIdsByTypePaged(
            @Path( ID ) UUID userId,
            @Path( TYPE ) UUID typeId,
            @Path( PAGE ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize);

    @GET( CONTROLLER + VERSIONED + OBJECTS + USER_ID_PATH + TYPE_ID_PATH + PAGE_SIZE_PATH + PAGE_PATH )
    Set<VersionedObjectKey> getVersionedObjectKeysByTypePaged(
            @Path( ID ) UUID userId,
            @Path( TYPE ) UUID typeId,
            @Path( PAGE ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize);

    /**
     * @deprecated Use {@link TypesApi}
     * @param typeName
     * @return
     */
    @Deprecated
    @GET( CONTROLLER + OBJECTS + TYPE_NAME_PATH )
    UUID getTypeForName( @Path( TYPE ) String typeName);
}
