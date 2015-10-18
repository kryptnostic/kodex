package com.kryptnostic.storage.v2.http;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.EncryptableBlock;
import com.kryptnostic.storage.v1.models.KryptnosticObject;
import com.kryptnostic.storage.v1.models.ObjectMetadata;
import com.kryptnostic.storage.v2.models.PendingObjectRequest;

public interface ObjectStorageApi {
    String CONTROLLER             = "/object";

    // Parameter names
    String ID                     = Names.ID_FIELD;
    String CONTENT                = "content";
    String BLOCK                  = "block";
    String USER                   = "user";

    // Paths
    String OBJECT_ID_PATH         = "/{" + ID + "}";
    String BLOCK_PATH             = "/{" + BLOCK + "}";
    String OFFSET                 = "offset";
    String PAGE_SIZE              = "pageSize";
    String OBJECT_LIST_PAGED_PATH = "/{" + OFFSET + "}/{" + PAGE_SIZE + "}";
    String CONTENTS_PATH          = "/" + Names.CONTENTS;

    String IV_PATH                = "/iv";
    String SALT_PATH              = "/salt";
    String TAG_PATH               = "/tag";
    String TYPE                   = "type";
    String TYPE_PATH              = "/type/{" + TYPE + "}";
    String OBJECT_APPEND_PATH     = "/append";
    String OBJECT_METADATA_PATH   = "/metadata";

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     * @throws BadRequestException Request was invalid
     * @throws ResourceNotFoundException
     */
    @POST( CONTROLLER )
    UUID createPendingObject( @Body PendingObjectRequest request ) throws ResourceNotFoundException;

    @PUT( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH )
    Response setObjectBlockCiphertext(
            @Path( ID ) UUID objectId,
            @Path( BLOCK ) int block,
            @Body BlockCiphertext ciphertext );

    @GET( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH )
    BlockCiphertext getObjectBlockCiphertext( @Path( ID ) UUID objectId, @Path( BLOCK ) int block );

    /**
     * Sets the IV for an object block
     * 
     * @param objectId
     * @param block
     * @param iv
     * @return
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + IV_PATH )
    Response setObjectBlockCiphertextIv( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] iv );

    @PUT( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + CONTENTS_PATH )
    Response setObjectBlockCiphertextContent( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] content );

    @PUT( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + SALT_PATH )
    Response setObjectBlockCiphertextSalt( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] salt );

    @PUT( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + TAG_PATH )
    Response setObjectBlockCiphertextTag( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] tag );

    @GET( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + IV_PATH )
    byte[] getObjectIV( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] iv );

    @GET( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + CONTENTS_PATH )
    byte[] getObjectBlockCiphertextContent( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] content );

    @GET( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + SALT_PATH )
    byte[] getObjectBlockCiphertextSalt( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] salt );

    @GET( CONTROLLER + OBJECT_ID_PATH + BLOCK_PATH + TAG_PATH )
    byte[] getObjectBlockCiphertextTag( @Path( ID ) UUID objectId, @Path( BLOCK ) int block, @Body byte[] tag );

    /**
     * Request an existing object be put into a pending state
     *
     * @param objectId
     * @return
     * @throws ResourceLockedException if the object is currently pending
     * @throws ResourceNotFoundException the object doesnt exist
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH )
    Response createPendingObjectFromExisting( @Path( ID ) UUID objectId ) throws ResourceLockedException,
            ResourceNotFoundException;

    /**
     * Retrieve an object's contents
     *
     * @param objectId
     * @return Contents of object
     */
    @GET( CONTROLLER + OBJECT_ID_PATH )
    KryptnosticObject getObject( @Path( ID ) UUID objectId ) throws ResourceNotFoundException;

    @GET( CONTROLLER + OBJECT_ID_PATH + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) UUID id ) throws ResourceNotFoundException;

    @POST( CONTROLLER )
    Map<UUID, List<BlockCiphertext>> getObjects( @Body Set<UUID> objectIds ) throws ResourceNotFoundException;

    @GET( CONTROLLER + TYPE_PATH )
    BasicResponse<Collection<String>> getObjectIdsByType( @Path( TYPE ) String type );

    @POST( CONTROLLER + OBJECT_ID_PATH + "/blocks" )
    BasicResponse<List<EncryptableBlock>> getObjectBlocks( @Path( ID ) String id, @Body List<Integer> indices )
            throws ResourceNotFoundException;

    @DELETE( CONTROLLER + OBJECT_ID_PATH )
    BasicResponse<String> delete( @Path( ID ) String id );

    @POST( CONTROLLER + OBJECT_ID_PATH + OBJECT_APPEND_PATH )
    BasicResponse<String> appendObject( @Path( ID ) String objectId, @Body EncryptableBlock blockToAppend )
            throws ResourceNotFoundException;

}
