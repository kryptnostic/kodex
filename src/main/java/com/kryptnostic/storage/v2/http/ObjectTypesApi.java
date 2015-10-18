package com.kryptnostic.storage.v2.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt; 
 *
 */
public interface ObjectTypesApi {
    String CONTROLLER = "/types";

    String TYPE       = "type";
    String TYPE_PATH  = "/{" + TYPE + "}";

    /**
     * @param typeObjectIds A set of encrypted type objects stored via the storage api.
     * @return A map of type UUIDs to UUIDs of encrypted type objects stored via the storage api.
     */
    @POST( CONTROLLER )
    Map<UUID,UUID> registerTypes( @Body Set<UUID> typeObjectIds );

    @DELETE( CONTROLLER + TYPE)
    UUID deregisterType( UUID typeId );

    @GET( CONTROLLER )
    Set<UUID> getTypes();

}
