package com.kryptnostic.v2.storage.api;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.CreateMetadataObjectRequest;
import com.kryptnostic.v2.storage.models.CreateObjectRequest;
import com.kryptnostic.v2.storage.models.Event;
import com.kryptnostic.v2.storage.models.IndexSegmentRequest;
import com.kryptnostic.v2.storage.models.ObjectMetadata;
import com.kryptnostic.v2.storage.models.ObjectMetadataEncryptedNode;
import com.kryptnostic.v2.storage.models.ObjectTreeLoadRequest;
import com.kryptnostic.v2.storage.models.ObjectTreeLoadResponse;
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
    String CONTROLLER                    = "/object";
    // Parameter names
    String ID                            = Names.ID_FIELD;
    String VERSION                       = "version";
    String TYPE_NAME_FIELD               = "type";
    String ROOT_OBJECT_ID_VAR            = "rootObjectId";
    String ROOT_OBJECT_VERSION_VAR       = "rootObjectVersion";
    String PAGE_SIZE_VAR                 = "pageSize";
    String LAST_OBJECT_ID_VAR            = "lastObjectId";
    String LAST_OBJECT_VERSION_VAR       = "lastObjectVersion";

    // Paths
    String OBJECT_ID_PATH                = "/id/{" + ID + "}";
    String VERSION_PATH                  = "/{" + VERSION + "}";
    String ROOT_OBJECT_ID_PATH           = "/{" + ROOT_OBJECT_ID_VAR + "}";
    String ROOT_OBJECT_VERSION_PATH      = "/{" + ROOT_OBJECT_VERSION_VAR + "}";
    String PAGE_SIZE_PATH                = "/{" + PAGE_SIZE_VAR + "}";
    String LAST_OBJECT_ID_PATH           = "/{" + LAST_OBJECT_ID_VAR + "}";
    String LAST_OBJECT_VERSION_PATH      = "/{" + LAST_OBJECT_VERSION_VAR + "}";
    String CONTENTS_PATH                 = "/" + Names.CONTENTS;

    String OBJECT_IDS_PATH               = "/ids";
    String IV_PATH                       = "/iv";
    String SALT_PATH                     = "/salt";
    String TAG_PATH                      = "/tag";
    String LEVELS_PATH                   = "/levels";
    String METADATA_PATH                 = "/metadata";
    String BULK_PATH                     = "/bulk";
    String LATEST                        = "/latest";
    String OBJECTMETADATA_PATH           = "/objectmetadata";
    String TYPE_PATH                     = "/type";
    String PREV_PAGE_PATH                = "/prev";
    String NEXT_PAGE_PATH                = "/next";
    String INDEX_SEGMENTS_PATH           = "/index-segments";

    String OBJECT_METADATA_PATH          = OBJECTMETADATA_PATH + OBJECT_ID_PATH;
    String VERSIONED_OBJECT_ID_PATH      = OBJECT_ID_PATH + VERSION_PATH;
    String LATEST_OBJECT_ID_PATH         = LATEST + OBJECT_ID_PATH;
    String LATEST_OBJECT_IDS_PATH        = LATEST + OBJECT_IDS_PATH;
    String ROOT_OBJECT_KEY_PATH          = ROOT_OBJECT_ID_PATH + ROOT_OBJECT_VERSION_PATH;
    String LAST_OBJECT_KEY_PATH          = LAST_OBJECT_ID_PATH + LAST_OBJECT_VERSION_PATH;

    String FULL_LEVELS_INITIAL_PAGE_PATH = LEVELS_PATH + PAGE_SIZE_PATH + ROOT_OBJECT_KEY_PATH;
    String FULL_LEVELS_PREV_PAGE_PATH    = LEVELS_PATH + PREV_PAGE_PATH + PAGE_SIZE_PATH + ROOT_OBJECT_KEY_PATH + LAST_OBJECT_KEY_PATH;
    String FULL_LEVELS_NEXT_PAGE_PATH    = LEVELS_PATH + NEXT_PAGE_PATH + PAGE_SIZE_PATH + ROOT_OBJECT_KEY_PATH + LAST_OBJECT_KEY_PATH;
    String FULL_UPDATE_TYPE_NAME_PATH    = OBJECT_ID_PATH + TYPE_PATH;

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     */
    @POST( CONTROLLER )
    VersionedObjectKey createObject( @Body CreateObjectRequest request );

    @POST( CONTROLLER + VERSIONED_OBJECT_ID_PATH + INDEX_SEGMENTS_PATH )
    Response createIndexSegments(
            @Path( ID ) UUID parentId,
            @Path( VERSION ) long parentVersion,
            @Body List<IndexSegmentRequest> requests);

    @GET( CONTROLLER + LATEST_OBJECT_ID_PATH )
    VersionedObjectKey getLatestVersionedObjectKey( @Path( ID ) UUID id);

    @GET( CONTROLLER + LATEST_OBJECT_IDS_PATH )
    Set<VersionedObjectKey> getLatestVersionedObjectKeys( @Body Set<UUID> objectIds );

    /**
     * Lazy Person API for bulk reading base64 encoded block ciphertexts in bulk.
     *
     * @param objectIds
     * @return
     */
    @POST( CONTROLLER + BULK_PATH )
    Map<UUID, BlockCiphertext> getObjects( @Body Set<UUID> objectIds );
    // TODO: Consider adding an API the returns the version as part of the value.

    /**
     * Lazy Person API for writing base64 encoded block ciphertexts. Objects written via this API will be available
     * through the more efficient byte level APIs.
     *
     * @param objectId
     * @param ciphertext
     * @return
     */
    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH )
    Response setObjectFromBlockCiphertext(
            @Path( ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body BlockCiphertext ciphertext);

    
    /**
     * API for storing plaintext events
     *
     * @param objectId
     * @param ciphertext
     * @return
     */
    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH )
    Response setEvent(
            @Path( ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body Event ciphertext);

    
    /**
     * Cached Lazy Person API for reading base64 encoded block ciphertexts. Objects readable by this API will be
     * available through the more efficient byte level APIs.
     *
     * @param objectId
     * @param version
     * @return
     */
    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH )
    BlockCiphertext getObjectAsBlockCiphertext( @Path( ID ) UUID objectId, @Path( VERSION ) long version);

    /**
     * Sets the IV for an object block
     *
     * @param objectId
     * @param version
     * @param iv
     * @return
     */
    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH + IV_PATH )
    Response setObjectIv( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] iv);

    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH + CONTENTS_PATH )
    Response setObjectContent( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] content);

    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH + SALT_PATH )
    Response setObjectSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] salt);

    @PUT( CONTROLLER + VERSIONED_OBJECT_ID_PATH + TAG_PATH )
    Response setObjectTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version, @Body byte[] tag);

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + CONTENTS_PATH )
    byte[] getObjectContent( @Path( ID ) UUID objectId, @Path( VERSION ) long version);

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + IV_PATH )
    byte[] getObjectIV( @Path( ID ) UUID objectId, @Path( VERSION ) long version);

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + SALT_PATH )
    byte[] getObjectSalt( @Path( ID ) UUID objectId, @Path( VERSION ) long version);

    @GET( CONTROLLER + VERSIONED_OBJECT_ID_PATH + TAG_PATH )
    byte[] getObjectTag( @Path( ID ) UUID objectId, @Path( VERSION ) long version);

    @GET( CONTROLLER + OBJECT_METADATA_PATH )
    ObjectMetadata getObjectMetadata( @Path( ID ) UUID id);

    @DELETE( CONTROLLER + VERSIONED_OBJECT_ID_PATH )
    Response delete( @Path( ID ) UUID id, @Path( VERSION ) long version);

    @DELETE( CONTROLLER + OBJECT_ID_PATH )
    Response delete( @Path( ID ) UUID id);

    @DELETE( CONTROLLER )
    Set<UUID> deleteObjectTrees( @Body Set<UUID> objectTrees );

    @POST( CONTROLLER + LEVELS_PATH )
    Map<UUID, ObjectMetadataEncryptedNode> getObjectsByTypeAndLoadLevel( @Body ObjectTreeLoadRequest request );

    @POST( CONTROLLER + FULL_LEVELS_INITIAL_PAGE_PATH )
    ObjectTreeLoadResponse getObjectsByTypeAndLoadLevelPaged(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( PAGE_SIZE_VAR ) int pageSize,
            ObjectTreeLoadRequest request);

    @POST( CONTROLLER + FULL_LEVELS_PREV_PAGE_PATH )
    ObjectTreeLoadResponse getObjectsByTypeAndLoadLevelPagedBackwards(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( LAST_OBJECT_ID_VAR ) UUID lastObjectId,
            @Path( LAST_OBJECT_VERSION_VAR ) long lastObjectVersion,
            @Path( PAGE_SIZE_VAR ) int pageSize,
            @Body ObjectTreeLoadRequest request);

    @POST( CONTROLLER + FULL_LEVELS_NEXT_PAGE_PATH )
    ObjectTreeLoadResponse getObjectsByTypeAndLoadLevelPagedForwards(
            @Path( ROOT_OBJECT_ID_VAR ) UUID rootObjectId,
            @Path( ROOT_OBJECT_VERSION_VAR ) long rootObjectVersion,
            @Path( LAST_OBJECT_ID_VAR ) UUID lastObjectId,
            @Path( LAST_OBJECT_VERSION_VAR ) long lastObjectVersion,
            @Path( PAGE_SIZE_VAR ) int pageSize,
            @Body ObjectTreeLoadRequest request);

    // METADATA APIs

    /**
     * Request a new object be created in a pending state
     *
     * @return The ID of the newly created object
     */
    @POST( CONTROLLER + METADATA_PATH )
    VersionedObjectKey createMetadataObject( @Body CreateMetadataObjectRequest request );

    @POST( CONTROLLER + FULL_UPDATE_TYPE_NAME_PATH )
    UUID updateTypeForObject( @Path( ID ) UUID objectId, @Body String typeName);

}
