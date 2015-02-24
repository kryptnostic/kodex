package com.kryptnostic.storage.v1.http;

import java.util.Collection;
import java.util.List;

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
import com.kryptnostic.storage.v1.models.KryptnosticObject;
import com.kryptnostic.storage.v1.models.ObjectMetadata;
import com.kryptnostic.storage.v1.models.request.PendingObjectRequest;

public interface ObjectApi {
    final String OBJECT                 = "/object";
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
     */
    @PUT( OBJECT )
    BasicResponse<String> createPendingObject( @Body PendingObjectRequest request ) throws BadRequestException;

    /**
     * Request an existing object be put into a pending state
     * 
     * @param id
     * @return
     * @throws ResourceLockedException if the object is currently pending
     * @throws ResourceNotFoundException the object doesnt exist
     */
    @PUT( OBJECT + OBJECT_ID_PATH )
    BasicResponse<String> createPendingObject( @Path( ID ) String id ) throws ResourceLockedException,
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
    @POST( OBJECT + OBJECT_ID_PATH )
    BasicResponse<String> updateObject( @Path( ID ) String id, @Body EncryptableBlock block )
            throws ResourceNotFoundException, ResourceNotLockedException, BadRequestException;

    /**
     * Retrieve an object's contents
     * 
     * @param id
     * @return Contents of object
     */
    @GET( OBJECT + OBJECT_ID_PATH )
    KryptnosticObject getObject( @Path( ID ) String id ) throws ResourceNotFoundException;

    @GET( OBJECT + OBJECT_ID_PATH + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) String id ) throws ResourceNotFoundException;

    @POST( OBJECT )
    BasicResponse<List<KryptnosticObject>> getObjects( @Body List<String> objectIds ) throws ResourceNotFoundException;

    /**
     * 
     * @return Collection of object ids
     */
    @GET( OBJECT )
    BasicResponse<Collection<String>> getObjectIds();

    @GET( OBJECT + OBJECT_LIST_PAGED_PATH )
    BasicResponse<Collection<String>> getObjectIds( @Path( OFFSET ) Integer offset, @Path( PAGE_SIZE ) Integer pageSize );

    @GET( OBJECT + TYPE_PATH + OBJECT_LIST_PAGED_PATH )
    BasicResponse<Collection<String>> getObjectIdsByType(
            @Path( TYPE ) String type,
            @Path( OFFSET ) Integer offset,
            @Path( PAGE_SIZE ) Integer pageSize );

    @GET( OBJECT + TYPE_PATH )
    BasicResponse<Collection<String>> getObjectIdsByType( @Path( TYPE ) String type );

    @POST( OBJECT + OBJECT_ID_PATH + "/blocks" )
    BasicResponse<List<EncryptableBlock>> getObjectBlocks( @Path( ID ) String id, @Body List<Integer> indices )
            throws ResourceNotFoundException;

    @DELETE( OBJECT + OBJECT_ID_PATH )
    BasicResponse<String> delete( @Path( ID ) String id );

    @POST( OBJECT + OBJECT_ID_PATH + OBJECT_APPEND_PATH )
    BasicResponse<String> appendObject( @Path( ID ) String objectId, @Body EncryptableBlock blockToAppend )
            throws ResourceNotFoundException;

}
