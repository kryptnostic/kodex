package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;
import com.kryptnostic.v2.storage.models.VersionedObjectKeySet;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface KeyStorageApi {
    String CONTROLLER                         = "/keys";
    // FHE
    String FHE_PATH                           = "/fhe";
    String FHE_PUBLIC_PATH                    = FHE_PATH + "/public";
    String FHE_PRIVATE_PATH                   = FHE_PATH + "/private";
    String FHE_SEARCH_PRIVATE                 = FHE_PATH + "/searchprivate";
    String FHE_HASH                           = FHE_PATH + "/hash";

    String RSA_PATH                           = "/rsa";
    String RSA_PUBLIC_KEY_PATH                = RSA_PATH + "/public";
    String RSA_PRIVATE_KEY_PATH               = RSA_PATH + "/private";

    String SALT_PATH                          = "/salt";
    String CRYPTO_SERVICE_PATH                = "/cryptoservice";
    String CRYPTO_SERVICES_PATH               = "/cryptoservices";
    String VERSIONED_CRYPTO_SERVICES_PATH     = "/versioned/cryptoservices";

    String AES_MASTER_KEY                     = "/aes";
    String AES_CRYPTO_SERVICE_PATH            = AES_MASTER_KEY + "/cryptoservice";
    String AES_CRYPTO_SERVICE_MIGRATION_PATH  = AES_MASTER_KEY + "/cryptoservice-migration";
    String AES_CRYPTO_SERVICES_PATH           = AES_MASTER_KEY + "/cryptoservices";
    String VERSIONED_AES_CRYPTO_SERVICES_PATH = AES_MASTER_KEY + "/versioned/cryptoservices";

    String OBJECT_ID                          = Names.ID_FIELD;
    String VERSION                            = "version";
    String USER                               = "user";
    String USER_ID_PATH                       = "/{" + USER + "}";

    String OBJECT_ID_PATH                     = "/id/{" + OBJECT_ID + "}";
    String VERSION_PATH                       = "/{" + VERSION + "}";
    String BULK_PATH                          = "/bulk";

    String BULK_RSA_PATH                      = BULK_PATH + CRYPTO_SERVICES_PATH;

    @POST( CONTROLLER + RSA_PRIVATE_KEY_PATH )
    Response setRSAPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    @GET( CONTROLLER + RSA_PRIVATE_KEY_PATH )
    BlockCiphertext getRSAPrivateKey();

    @POST( CONTROLLER + RSA_PUBLIC_KEY_PATH )
    Response setRSAPublicKey( @Body byte[] publicKey );

    @GET( CONTROLLER + RSA_PUBLIC_KEY_PATH + USER_ID_PATH )
    byte[] getRSAPublicKey( @Path( USER ) UUID user);

    @POST( CONTROLLER + RSA_PUBLIC_KEY_PATH + BULK_PATH )
    Map<UUID, byte[]> getRSAPublicKeys( @Body Set<UUID> userIds );

    @POST( CONTROLLER + SALT_PATH + USER_ID_PATH )
    Response setEncryptedSalt( @Path( USER ) UUID user, @Body BlockCiphertext encryptedSalt);

    @GET( CONTROLLER + SALT_PATH + USER_ID_PATH )
    BlockCiphertext getEncryptedSalt( @Path( USER ) UUID user);

    @POST( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    Response setObjectCryptoService( @Path( OBJECT_ID ) UUID objectId, @Body byte[] crypto);

    /**
     * Uncached API to retrieves the object crypto service for the latest version of the object specified by
     * {@code objectId}
     *
     * @param objectId The object for which to retrieve the crypto service.
     * @return The crypto service corresponding the latest version of the object specified by {@code objectId}
     */
    @GET( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    byte[] getObjectCryptoService( @Path( OBJECT_ID ) UUID objectId);

    @PUT( CONTROLLER + AES_CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    Response setAesEncryptedObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body BlockCiphertext crypto);

    @PUT( CONTROLLER + AES_CRYPTO_SERVICE_MIGRATION_PATH + OBJECT_ID_PATH )
    Response setAesEncryptedObjectCryptoServiceForMigration(
            @Path( OBJECT_ID ) UUID objectId,
            @Body BlockCiphertext crypto);

    @GET( CONTROLLER + AES_CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    BlockCiphertext getAesEncryptedObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version);

    @GET( CONTROLLER + BULK_RSA_PATH )
    Map<UUID, byte[]> getRSACryptoServicesForUser();

    /**
     * Returns AES encrypted crypto services.
     *
     * @param ids
     * @return
     */
    @POST( CONTROLLER + VERSIONED_AES_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, BlockCiphertext> getAesEncryptedCryptoServices( @Body Set<UUID> ids );

    /**
     * Returns AES encrypted crypto services.
     *
     * @param ids
     * @return
     */
    @POST( CONTROLLER + AES_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, BlockCiphertext> getAesEncryptedCryptoServices( @Body VersionedObjectKeySet ids );

    @Timed
    @GET( CONTROLLER + AES_MASTER_KEY )
    byte[] getMasterCryptoService();

    @PUT( CONTROLLER + AES_MASTER_KEY )
    Response setMasterCryptoService( @Body byte[] masterKey );

    /**
     *
     * @param publicKey
     */
    @POST( CONTROLLER + FHE_PUBLIC_PATH )
    Response setFHEPublicKey( @Body byte[] publicKey );

    /**
     *
     * @param user
     */
    @GET( CONTROLLER + FHE_PUBLIC_PATH + USER_ID_PATH )
    byte[] getFHEPublicKey( @Path( USER ) UUID user);

    @POST( CONTROLLER + FHE_PRIVATE_PATH )
    Response setFHEPrivateKeyForCurrentUser( @Body BlockCiphertext key );

    @GET( CONTROLLER + FHE_PRIVATE_PATH )
    Optional<BlockCiphertext> getFHEPrivateKeyForCurrentUser();

    @POST( CONTROLLER + FHE_SEARCH_PRIVATE )
    Response setFHESearchPrivateKeyForCurrentUser( @Body BlockCiphertext key );

    @GET( CONTROLLER + FHE_SEARCH_PRIVATE )
    Optional<BlockCiphertext> getFHESearchPrivateKeyForUser();

    @POST( CONTROLLER + FHE_HASH )
    Response setHashFunctionForCurrentUser( @Body byte[] key );

    /**
     * @return The byte level representation of the ClientHashFunction.
     */
    @GET( CONTROLLER + FHE_HASH )
    byte[] getHashFunctionForCurrentUser();

}