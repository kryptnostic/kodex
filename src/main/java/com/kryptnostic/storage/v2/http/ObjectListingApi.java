package com.kryptnostic.storage.v2.http;

import java.util.Set;
import java.util.UUID;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectListingApi {
    String CONTROLLER             = "/objects";
    String TYPE                   = "type";
    String OFFSET                 = "offset";
    String PAGE_SIZE              = "pageSize";

    String OBJECT_LIST_PAGED_PATH = "/{" + OFFSET + "}/{" + PAGE_SIZE + "}";
    String TYPE_PATH              = "/type/{" + TYPE + "}";

    /**
     *
     * @return Collection of object ids
     */
    @GET( CONTROLLER )
    Set<UUID> getObjectIds();

    @GET( CONTROLLER + OBJECT_LIST_PAGED_PATH )
    Set<UUID> getObjectIds( @Path( OFFSET ) Integer offset, @Path( PAGE_SIZE ) Integer pageSize );

    @GET( CONTROLLER + TYPE_PATH + OBJECT_LIST_PAGED_PATH )
    Set<UUID> getObjectIdsByType( @Path( TYPE ) UUID type );

    @GET( CONTROLLER + TYPE_PATH + OBJECT_LIST_PAGED_PATH )
    Set<UUID> getObjectIdsByTypePaged(
            @Path( TYPE ) UUID type,
            @Path( OFFSET ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize );
}
