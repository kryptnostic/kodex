package com.kryptnostic.storage.v2.models;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotLockedException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.EncryptableBlock;
import com.kryptnostic.storage.v1.models.ObjectMetadata;
import com.kryptnostic.storage.v1.models.request.PendingObjectRequest;

public interface ObjectStorageApi {
    final String CONTROLLER             = "/object";
    final String ID                     = "id";
    final String REALM                  = "realm";
    final String USER                   = "user";
    final String OBJECT_ID_PATH         = "/{" + ID + "}";
    final String OFFSET                 = "offset";
    final String PAGE_SIZE              = "pageSize";
    final String OBJECT_LIST_PAGED_PATH = "/{" + OFFSET + "}/{" + PAGE_SIZE + "}";
    final String TYPE                   = "type";
    final String TYPE_PATH              = "/type/{" + TYPE + "}";
    final String OBJECT_APPEND_PATH     = "/append";
    final String OBJECT_METADATA_PATH   = "/metadata";

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     * @throws BadRequestException Request was invalid
     * @throws ResourceNotFoundException
     */
    @PUT( CONTROLLER )
    BasicResponse<UUID> createPendingObject( @Body PendingObjectRequest request ) throws BadRequestException, ResourceNotFoundException;

    /**
     * Request an existing object be put into a pending state
     *
     * @param id
     * @return
     * @throws ResourceLockedException if the object is currently pending
     * @throws ResourceNotFoundException the object doesnt exist
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH )
    BasicResponse<UUID> createPendingObjectFromExisting( @Path( ID ) UUID id ) throws ResourceLockedException,
            ResourceNotFoundException;

    /**
     * Update a object using an EncryptableBlock
     *
     * Precondition: object is in a pending state
     *
     * Postcondition: If this is the last block required to make the object valid, the object will be put into a
     * non-pending state and be available for reading
     *
     * @param id Id of object to update
     * @param block A single block for the object
     * @return The progress and verification data for the updated object
     * @throws ResourceNotFoundException if specified objectId was not found
     * @throws ResourceNotLockedException if the specified object has not been locked for updating
     * @throws BadRequestException if the block is invalid
     */
    @POST( CONTROLLER + OBJECT_ID_PATH )
    BasicResponse<UUID> updateObject( @Path( ID ) UUID id, @Body EncryptableBlock block )
            throws ResourceNotFoundException, ResourceNotLockedException, BadRequestException;

    @GET( CONTROLLER + OBJECT_ID_PATH + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) UUID id ) throws ResourceNotFoundException;

    /**
     *
     * @return Collection of object ids
     */
    @GET( CONTROLLER )
    BasicResponse<Collection<UUID>> getObjectIds();

    @GET( CONTROLLER + OBJECT_LIST_PAGED_PATH )
    BasicResponse<Collection<UUID>> getObjectIds( @Path( OFFSET ) Integer offset, @Path( PAGE_SIZE ) Integer pageSize );

    @GET( CONTROLLER + TYPE_PATH + OBJECT_LIST_PAGED_PATH )
    BasicResponse<Collection<UUID>> getObjectIdsByType(
            @Path( TYPE ) String type,
            @Path( OFFSET ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize );

    @GET( CONTROLLER + TYPE_PATH )
    BasicResponse<Collection<UUID>> getObjectIdsByType( @Path( TYPE ) String type );

    @POST( CONTROLLER + OBJECT_ID_PATH + "/blocks" )
    BasicResponse<List<EncryptableBlock>> getObjectBlocks( @Path( ID ) UUID uuid, @Body List<Integer> indices )
            throws ResourceNotFoundException;

    @DELETE( CONTROLLER + OBJECT_ID_PATH )
    BasicResponse<String> delete( @Path( ID ) UUID id );

    @POST( CONTROLLER + OBJECT_ID_PATH + OBJECT_APPEND_PATH )
    BasicResponse<UUID> appendObject( @Path( ID ) UUID objectId, @Body EncryptableBlock blockToAppend )
            throws ResourceNotFoundException;


}
