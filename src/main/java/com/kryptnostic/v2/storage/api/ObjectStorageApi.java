package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.CreateMetadataObjectRequest;
import com.kryptnostic.v2.storage.models.CreateObjectRequest;
import com.kryptnostic.v2.storage.models.ObjectMetadata;
import com.kryptnostic.v2.storage.models.ObjectMetadataEncryptedNode;
import com.kryptnostic.v2.storage.models.ObjectTreeLoadRequest;
import com.kryptnostic.v2.storage.models.PaddedMetadataObjectIds;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface ObjectStorageApi {
    String CONTROLLER           = "/object";
    // Parameter names
    String ID                   = Names.ID_FIELD;
    String VERSION              = "version";
    String BLOCK                = "block";

    // Paths
    String OBJECT_ID_PATH       = "/id/{" + ID + "}";
    String VERSION_PATH         = "/{" + VERSION + "}";
    String CONTENTS_PATH        = "/" + Names.CONTENTS;

    String IV_PATH              = "/iv";
    String SALT_PATH            = "/salt";
    String TAG_PATH             = "/tag";
    String LEVELS_PATH          = "/levels";
    String METADATA_PATH        = "/metadata";
    String BULK_PATH            = "/bulk";
    String LATEST               = "/latest";
    String OBJECTMETADATA_PATH      = "/objectmetadata";

    String OBJECT_METADATA_PATH     = OBJECTMETADATA_PATH + OBJECT_ID_PATH;
    String VERSIONED_OBJECT_ID_PATH = OBJECT_ID_PATH + VERSION_PATH;
    String LATEST_OBJECT_ID_PATH = LATEST + OBJECT_ID_PATH;

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     */
    @POST( CONTROLLER )
    VersionedObjectKey createObject( @Body CreateObjectRequest request );

    @GET( CONTROLLER + LATEST_OBJECT_ID_PATH )
    VersionedObjectKey getLatestVersionedObjectKey( @Path( ID ) UUID id );

    /**
     * Lazy Person API for bulk reading base64 encoded block ciphertexts in bulk.
     *
     * @param objectIds
     * @return
     */
    @POST( CONTROLLER + BULK_PATH )
    Map<UUID, BlockCiphertext> getObjects( @Body Set<UUID> objectIds );
    //TODO: Consider adding an API the returns the version as part of the value.

    /**
     * Lazy Person API for writing base64 encoded block ciphertexts. Objects written via this API will be available
     * through the more efficient byte level APIs.
     *
     * @param objectId
     * @param ciphertext
     * @return
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH )
    Response setObjectFromBlockCiphertext(
            @Path( ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body BlockCiphertext ciphertext );

    /**
     * Cached Lazy Person API for reading base64 encoded block ciphertexts. Objects readable by this API will be
     * available through the more efficient byte level APIs.
     *
     * @param objectId
     * @param version
     * @return
     */
    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH )
    BlockCiphertext getObjectAsBlockCiphertext( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    /**
     * Sets the IV for an object block
     *
     * @param objectId
     * @param version
     * @param iv
     * @return
     */
    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + IV_PATH )
    Response setObjectIv( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] iv );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + CONTENTS_PATH )
    Response setObjectContent( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] content );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + SALT_PATH )
    Response setObjectSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] salt );

    @PUT( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH + TAG_PATH )
    Response setObjectTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] tag );

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + CONTENTS_PATH )
    byte[] getObjectContent( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + IV_PATH )
    byte[] getObjectIV( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + SALT_PATH )
    byte[] getObjectSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + TAG_PATH )
    byte[] getObjectTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version );

    @GET( CONTROLLER + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) UUID id );

    @DELETE( CONTROLLER + OBJECT_ID_PATH + VERSION_PATH )
    Response delete( @Path( ID ) UUID id, @Path( VERSION ) long version );

    @DELETE( CONTROLLER + OBJECT_ID_PATH )
    Response delete( @Path( ID ) UUID id );

    @DELETE( CONTROLLER )
    Set<UUID> deleteObjectTrees( @Body Set<UUID> objectTrees );

    @POST( CONTROLLER + LEVELS_PATH )
    Map<UUID, ObjectMetadataEncryptedNode> getObjectsByTypeAndLoadLevel( @Body ObjectTreeLoadRequest request );

    // METADATA APIs

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     */
    @POST( CONTROLLER + METADATA_PATH )
    VersionedObjectKey createMetadataObject( @Body CreateMetadataObjectRequest request );

    @POST( CONTROLLER + METADATA_PATH + OBJECT_ID_PATH )
    Response createMetadataEntry(
            @Path( ID ) UUID objectId,
            @Body Set<PaddedMetadataObjectIds> paddedMetadataObjectIds );

    @DELETE( CONTROLLER + METADATA_PATH + OBJECT_ID_PATH )
    Response createMetadataEntry( @Path( ID ) UUID objectId );
}
