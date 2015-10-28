package com.kryptnostic.storage.v2.http;

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

import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.storage.v2.models.CreateMetadataObjectRequest;
import com.kryptnostic.storage.v2.models.CreateObjectRequest;
import com.kryptnostic.storage.v2.models.ObjectMetadata;
import com.kryptnostic.storage.v2.models.ObjectMetadataNode;
import com.kryptnostic.storage.v2.models.ObjectTreeLoadRequest;
import com.kryptnostic.storage.v2.models.PaddedMetadataObjectIds;
import com.kryptnostic.storage.v2.models.VersionedObjectKey;
import com.kryptnostic.v2.constants.Names;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectStorageApi {
    String CONTROLLER           = "/object";
    // Parameter names
    String ID                   = Names.ID_FIELD;
    String VERSION              = "version";
    String CONTENT              = "content";
    String BLOCK                = "block";
    String USER                 = "user";

    // Paths
    String OBJECT_ID_PATH       = "/id/{" + ID + "}";
    String VERSION_PATH         = "/{" + VERSION + "}";
    String BLOCK_PATH           = "/{" + BLOCK + "}";
    String USER_ID_PATH         = "/{" + ID + "}";
    String CONTENTS_PATH        = "/" + Names.CONTENTS;

    String IV_PATH              = "/iv";
    String SALT_PATH            = "/salt";
    String TAG_PATH             = "/tag";
    String LEVELS_PATH          = "/levels";
    String TYPE                 = "type";
    String TYPE_PATH            = "/type/{" + TYPE + "}";
    String OBJECT_APPEND_PATH   = "/append";
    String OBJECT_METADATA_PATH = "/metadata";
    String BULK_PATH            = "/bulk";

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     * @throws BadRequestException Request was invalid
     * @throws ResourceNotFoundException
     */
    @POST( CONTROLLER )
    VersionedObjectKey createObject( @Body CreateObjectRequest request );

    @GET( CONTROLLER + OBJECT_ID_PATH )
    VersionedObjectKey getVersionedObjectKey( @Path( ID ) UUID id );

    /**
     * Lazy Person API for writing base64 encoded block ciphertexts in bulk.
     *
     * @param objectIds
     * @return
     */
    @POST( CONTROLLER + BULK_PATH )
    Map<UUID, BlockCiphertext> getObjects( @Body Set<UUID> objectIds );

    /**
     * Lazy Person API for writing base64 encoded block ciphertexts. Objects written via this API will be available
     * through the more efficient byte level APIs.
     *
     * @param objectId
     * @param ciphertext
     * @return
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + BLOCK_PATH )
    Response setObjectBlockCiphertext(
            @Path( ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body BlockCiphertext ciphertext );

    /**
     * Cached Lazy Person API for reading base64 encoded block ciphertexts. Objects readable by this API will be
     * available through the more efficient byte level APIs.
     *
     * @param objectId
     * @param block
     * @return
     */
    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH )
    BlockCiphertext getObjectBlockCiphertext( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    /**
     * Sets the IV for an object block
     *
     * @param objectId
     * @param block
     * @param iv
     * @return
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + IV_PATH )
    Response setObjectBlockCiphertextIv( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] iv );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + CONTENTS_PATH )
    Response setObjectBlockCiphertextContent(
            @Path( ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body byte[] content );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + SALT_PATH )
    Response setObjectBlockCiphertextSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] salt );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + TAG_PATH )
    Response setObjectBlockCiphertextTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] tag );

    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + CONTENTS_PATH )
    byte[] getObjectBlockCiphertextContent( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + IV_PATH )
    byte[] getObjectIV( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + SALT_PATH )
    byte[] getObjectBlockCiphertextSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + TAG_PATH )
    byte[] getObjectBlockCiphertextTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + OBJECT_ID_PATH + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) UUID id );

    @DELETE( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH )
    Response delete( @Path( ID ) UUID id, @Path( VERSION ) long version );

    @DELETE( CONTROLLER )
    Set<UUID> deleteObjectTrees( @Body Set<UUID> objectTrees );

    @POST( CONTROLLER + LEVELS_PATH )
    Map<UUID, ObjectMetadataNode> getObjectsByTypeAndLoadLevel( @Body ObjectTreeLoadRequest request );

    // METADATA APIs

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     * @throws BadRequestException Request was invalid
     * @throws ResourceNotFoundException
     */
    @POST( CONTROLLER + OBJECT_METADATA_PATH )
    VersionedObjectKey createMetadataObject( @Body CreateMetadataObjectRequest request );

    @POST( CONTROLLER + OBJECT_METADATA_PATH + OBJECT_ID_PATH )
    Response createMetadataEntry(
            @Path( ID ) UUID objectId,
            @Body Set<PaddedMetadataObjectIds> paddedMetadataObjectIds );

    @DELETE( CONTROLLER + OBJECT_METADATA_PATH + OBJECT_ID_PATH )
    Response createMetadataEntry( @Path( ID ) UUID objectId );
}
