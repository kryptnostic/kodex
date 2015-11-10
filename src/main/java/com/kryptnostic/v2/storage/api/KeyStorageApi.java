package com.kryptnostic.v2.storage.api;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;
import com.kryptnostic.v2.storage.models.VersionedObjectKeySet;
import com.kryptnostic.v2.storage.models.keys.BootstrapKeyIds;

public interface KeyStorageApi {
    String CONTROLLER                     = "/keys";
    // FHE
    String FHE_PATH                       = "/fhe";
    String FHE_PUBLIC_PATH               = FHE_PATH + "/public";
    String FHE_PRIVATE_PATH               = FHE_PATH + "/private";
    String FHE_SEARCH_PRIVATE             = FHE_PATH + "/searchprivate";
    String FHE_HASH                       = FHE_PATH + "/hash";

    String RSA_PATH                       = "/rsa";
    String RSA_PUBLIC_KEY_PATH            = RSA_PATH + "/public";
    String RSA_PRIVATE_KEY_PATH           = RSA_PATH + "/private";

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


    @GET( CONTROLLER )
    BootstrapKeyIds getBootstrapInformation();

    @POST( CONTROLLER + RSA_PRIVATE_KEY_PATH )
    Optional<String> setRSAPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    @GET( CONTROLLER + RSA_PRIVATE_KEY_PATH )
    BlockCiphertext getRSAPrivateKey();

    @POST( CONTROLLER + RSA_PUBLIC_KEY_PATH )
    Optional<String> setRSAPublicKey( @Body byte[] publicKey );

    @GET( CONTROLLER + RSA_PUBLIC_KEY_PATH + USER_ID_PATH )
    byte[] getRSAPublicKey( @Path( USER ) UUID user );

    @POST( CONTROLLER + RSA_PUBLIC_KEY_PATH + BULK_PATH )
    Map<UUID, byte[]> getRSAPublicKeys( @Body Set<UUID> userIds );

    @POST( CONTROLLER + SALT_PATH + USER_ID_PATH )
    Optional<String> setEncryptedSalt( @Path( USER ) UUID user, @Body BlockCiphertext encryptedSalt );

    @GET( CONTROLLER + SALT_PATH + USER_ID_PATH )
    BlockCiphertext getEncryptedSalt( @Path( USER ) UUID user );

    @POST( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    Optional<String> setObjectCryptoService( @Path( OBJECT_ID ) UUID objectId, @Body byte[] crypto );

    /**
     * Uncached API to retrieves the object crypto service for the latest version of the object specified by
     * {@code objectId}
     *
     * @param objectId The object for which to retrieve the crypto service.
     * @return The crypto service corresponding the latest version of the object specified by {@code objectId}
     */
    @GET( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH )
    byte[] getObjectCryptoService( @Path( OBJECT_ID ) UUID objectId );

    @PUT( CONTROLLER + CRYPTO_SERVICE_PATH + OBJECT_ID_PATH + VERSION_PATH )
    Optional<String> setObjectCryptoService(
            @Path( OBJECT_ID ) UUID objectId,
            @Path( VERSION ) long version,
            @Body byte[] crypto );

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

    @POST( CONTROLLER + CRYPTO_SERVICES_PATH )
    Map<UUID, byte[]> getObjectCryptoServices( @Body Set<UUID> ids );

    @POST( CONTROLLER + VERSIONED_CRYPTO_SERVICES_PATH )
    Map<VersionedObjectKey, byte[]> getObjectCryptoServices( @Body VersionedObjectKeySet ids );

    /**
     *
     * @param publicKey
     */
    @POST( CONTROLLER + FHE_PUBLIC_PATH )
    void setFHEPublicKey( @Body byte[] publicKey );

    /**
     *
     * @param user
     * @return
     */
    @GET( CONTROLLER + FHE_PUBLIC_PATH + USER_ID_PATH )
    byte[] getFHEPublicKey( @Path( USER ) UUID user );

    @POST( CONTROLLER + FHE_PRIVATE_PATH )
    Optional<String> setFHEPrivateKeyForCurrentUser( @Body BlockCiphertext key );

    @GET( CONTROLLER + FHE_PRIVATE_PATH )
    Optional<BlockCiphertext> getFHEPrivateKeyForCurrentUser();

    @POST( CONTROLLER + FHE_SEARCH_PRIVATE )
    Optional<String> setFHESearchPrivateKeyForCurrentUser( @Body BlockCiphertext key );

    @GET( CONTROLLER + FHE_SEARCH_PRIVATE )
    Optional<BlockCiphertext> getFHESearchPrivateKeyForUser();

    @POST( CONTROLLER + FHE_HASH )
    Optional<String> setHashFunctionForCurrentUser( @Body byte[] key );

    /**
     * @return The byte level representation of the ClientHashFunction.
     * @throws BadRequestException
     */
    @GET( CONTROLLER + FHE_HASH )
    byte[] getHashFunctionForCurrentUser();
}