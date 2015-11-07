package com.kryptnostic.storage.v2.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.storage.v2.models.VersionedObjectKey;
import com.kryptnostic.storage.v2.models.VersionedObjectKeySet;

public interface KeyStorageApi {
    String CONTROLLER                     = "/keys";
    // FHE
    String FHE_PATH                       = "/fhe";
    String FHE_PRIVATE_PATH               = FHE_PATH + "/private";
    String FHE_SEARCH_PRIVATE             = FHE_PATH + "/searchprivate";
    String FHE_HASH                       = FHE_PATH + "/hash";

    String PUBLIC_KEY_PATH                = "/public";
    String RSA_PUBLIC                     = "/rsa";
    String SALT_PATH                      = "/salt";
    String CRYPTO_SERVICE_PATH            = "/cryptoservice";
    String CRYPTO_SERVICES_PATH           = "/cryptoservices";
    String VERSIONED_CRYPTO_SERVICES_PATH = "/versioned/cryptoservices";

    String OBJECT_ID                      = Names.ID_FIELD;
    String VERSION                        = "version";
    String USER                           = "user";
    String USER_ID_PATH                   = "/{" + USER + "}";

    String OBJECT_ID_PATH                 = "/id/{" + OBJECT_ID + "}";
    String VERSION_PATH                   = "/{" + VERSION + "}";
    String BULK_PATH                      = "/bulk";

    @POST( CONTROLLER + PUBLIC_KEY_PATH + BULK_PATH )
    Map<UUID, byte[]> getPublicKeys( @Body Set<UUID> userIds );

    @GET( CONTROLLER + PUBLIC_KEY_PATH + USER_ID_PATH )
    byte[] getPublicKey( @Path( USER ) UUID user );

    @POST( CONTROLLER + PUBLIC_KEY_PATH )
    void setPublicKey( @Body byte[] publicKey );

    @GET( CONTROLLER + FHE_PRIVATE_PATH )
    BlockCiphertext getPrivateKey();

    @POST( CONTROLLER + FHE_PRIVATE_PATH )
    void setPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    @GET( CONTROLLER + SALT_PATH + USER_ID_PATH )
    BlockCiphertext getEncryptedSalt( @Path( USER ) UUID user );

    @POST( CONTROLLER + SALT_PATH + USER_ID_PATH )
    void setEncryptedSalt( @Path( USER ) UUID user, @Body BlockCiphertext encryptedSalt );

    /**
     * Uncached API to retrieves the object crypto service for the latest version of the object specified by
     * {@code objectId}
     * 
     * @param objectId The object for which to retrieve the crypto service.
     * @return The crypto service corresponding the latest version of the object specified by {@code objectId}
     */
    @GET( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    byte[] getObjectCryptoService( @Path( OBJECT_ID ) UUID objectId );

    /**
     * Cached API to retrieve the object crypto service for a specific version of the object specified by
     * {@code objectId}
     * 
     * @param objectId The object for which to retrieve the crypto service.
     * @param version The version of the object for which to retrieve a crpyto service
     * @return The crypto service corresponding to the object with version specified by {@code version} and object id
     *         specified by {@code objectId}
     */
    @GET( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    byte[] getObjectCryptoService( @Path( OBJECT_ID ) UUID objectId, @Path( VERSION ) long version );

    @POST( CONTROLLER + VERSIONED_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, byte[]> getObjectCryptoServices( @Body VersionedObjectKeySet ids );

    @POST( CONTROLLER + CRYPTO_SERVICES_PATH )
    Map<UUID, byte[]> getObjectCryptoServices( @Body Set<UUID> ids );

    @POST( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    void setObjectCryptoService( @Path( OBJECT_ID ) UUID objectId, @Body byte[] crypto );

    @PUT( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    void setObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body byte[] crypto );

    @POST( CONTROLLER + FHE_PRIVATE_PATH )
    Optional<String> setFHEPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + FHE_PRIVATE_PATH )
    Optional<BlockCiphertext> getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @POST( CONTROLLER + FHE_SEARCH_PRIVATE )
    Optional<String> setFHESearchPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + FHE_SEARCH_PRIVATE )
    Optional<BlockCiphertext> getFHESearchPrivateKeyForUser() throws BadRequestException;

    @POST( CONTROLLER + FHE_HASH )
    Response setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    /**
     * @return The byte level representation of the ClientHashFunction.
     * @throws BadRequestException
     */
    @GET( CONTROLLER + FHE_HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;
}