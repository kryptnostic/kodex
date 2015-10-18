package com.kryptnostic.storage.v2.http;

import java.util.Set;
import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectTypesApi {
    String CONTROLLER   = "/types";

    String TYPE         = "type";
    String PRIVATE      = "private";
    String PUBLIC       = "public";
    String TYPE_PATH    = "/{" + TYPE + "}";
    String PRIVATE_PATH = "/{" + PRIVATE + "}";
    String PUBLIC_PATH  = "/{" + PUBLIC + "}";

    /**
     * @param typeObjectIds A set of encrypted type objects stored via the storage api.
     * @return A map of type UUIDs to UUIDs of encrypted type objects stored via the storage api.
     */
    @POST( CONTROLLER )
    Response registerPrivateTypes( @Body Set<UUID> typeObjectIds );

    @DELETE( CONTROLLER )
    Response deregisterPrivateTypes( @Body Set<UUID> typeObjectIds );
    
    @DELETE( CONTROLLER + TYPE_PATH )
    UUID deregisterType( @Path( TYPE ) UUID typeId );

    @GET( CONTROLLER )
    Set<UUID> getTypes();

}
