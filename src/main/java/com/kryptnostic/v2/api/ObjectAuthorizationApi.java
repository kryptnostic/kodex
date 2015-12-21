package com.kryptnostic.v2.api;

import java.util.UUID;

import com.kryptnostic.v2.constants.Names;

import retrofit.http.GET;
import retrofit.http.Path;

public interface ObjectAuthorizationApi {

    String CONTROLLER   = "/access";

    String READERS_PATH = "/readers";
    String WRITERS_PATH = "/writers";
    String OWNERS_PATH  = "/owners";

    String ID                = Names.ID_FIELD;
    String OBJECT_ID_PATH = "/{" + ID + "}";

    String FULL_READERS_PATH = READERS_PATH + OBJECT_ID_PATH;
    String FULL_WRITERS_PATH = WRITERS_PATH + OBJECT_ID_PATH;
    String FULL_OWNERS_PATH  = OWNERS_PATH + OBJECT_ID_PATH;

    @GET( CONTROLLER + FULL_READERS_PATH )
    Iterable<UUID> getUsersWithReadAccess( @Path( ID ) UUID objectId);

    @GET( CONTROLLER + FULL_WRITERS_PATH )
    Iterable<UUID> getUsersWithWriteAccess( @Path( ID ) UUID objectId);

    @GET( CONTROLLER + FULL_OWNERS_PATH )
    Iterable<UUID> getUsersWithOwnerAccess( @Path( ID ) UUID objectId);

}
