package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;
import com.kryptnostic.v2.storage.models.VersionedObjectKeySet;
import com.kryptnostic.v2.storage.models.keys.BootstrapKeyIds;

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
    String AES_CRYPTO_SERVICE_PATH            = "/aescryptoservice";
    String AES_CRYPTO_SERVICES_PATH           = "/aescryptoservices";
    String VERSIONED_AES_CRYPTO_SERVICES_PATH = "/versioned/aescryptoservices";

    String OBJECT_ID                          = Names.ID_FIELD;
    String VERSION                            = "version";
    String USER                               = "user";
    String USER_ID_PATH                       = "/{" + USER + "}";

    String OBJECT_ID_PATH                     = "/id/{" + OBJECT_ID + "}";
    String VERSION_PATH                       = "/{" + VERSION + "}";
    String BULK_PATH                          = "/bulk";

    @GET( CONTROLLER )
    BootstrapKeyIds getBootstrapInformation();

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

    /**
     * @deprecated Use {@link KeyStorageApi#setAesEncryptedObjectCryptoService(UUID, long, BlockCiphertext)} instead.
     * @param objectId The UUID of the object for which to set a crypto service.
     * @param version The version of the object.
     * @param crypto An encrypted content protection keys
     * @return An empty body with HTTP 200 status code.
     */
    @Deprecated
    @PUT( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    Response setObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body byte[] crypto);

    @PUT( CONTROLLER + AES_CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    Response setAesEncryptedObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body BlockCiphertext crypto);

    /**
     * Cached API to retrieve the object crypto service for a specific version of the object specified by
     * {@code objectId}
     * 
     * @deprecated Use {@link KeyStorageApi#getAesEncryptedObjectCryptoService(UUID, long)} instead.
     * @param objectId The object for which to retrieve the crypto service.
     * @param version The version of the object for which to retrieve a crpyto service
     * @return The crypto service corresponding to the object with version specified by {@code version} and object id
     */
    @Deprecated
    @GET( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    byte[] getObjectCryptoService( @Path( OBJECT_ID ) UUID objectId, @Path( VERSION ) long version);

    @GET( CONTROLLER + AES_CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    BlockCiphertext getAesEncryptedObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version);

    /**
     * Gets the crypto encrypted crypto services corresponding to a set of UUIDs. This call automatically resolves an
     * object uuid to its latest version.
     * 
     * @deprecated Use {@link KeyStorageApi#getAesEncryptedCryptoServices(VersionedObjectKeySet)} instead.
     * @param ids A set of UUID for which to retrieve cryptoservices.
     * @return A map pairing object UUIDs with their corresponding cryptoservices.
     */
    @Deprecated
    @POST( CONTROLLER + CRYPTO_SERVICES_PATH )
    Map<UUID, byte[]> getObjectCryptoServices( @Body Set<UUID> ids );

    /**
     * Gets the crypto encrypted crypto services corresponding to a set of UUIDs.
     * 
     * @deprecated Use {@link KeyStorageApi#getAesEncryptedCryptoServices(VersionedObjectKeySet)} instead.
     * @param objectKeys A set of VersiondObjectKeys for which to retrieve cryptoservices.
     * @return A map pairing VersiondObjectKeys with their corresponding cryptoservices.
     */
    @Deprecated
    @POST( CONTROLLER + VERSIONED_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, byte[]> getObjectCryptoServices( @Body VersionedObjectKeySet objectKeys );

    /**
     * Returns AES encrypted crypto services.
     * 
     * @param ids
     * @return
     */
    @POST( CONTROLLER + VERSIONED_AES_CRYPTO_SERVICES_PATH )
    Map<UUID, BlockCiphertext> getAesEncryptedCryptoServices( @Body Set<UUID> ids );

    /**
     * Returns AES encrypted crypto services.
     * 
     * @param ids
     * @return
     */
    @POST( CONTROLLER + AES_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, BlockCiphertext> getAesEncryptedCryptoServices( @Body VersionedObjectKeySet ids );

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