package com.kryptnostic.storage.v2.http;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface KeyStorageApi {
    static final String CONTROLLER           = "/keys";
    // FHE
    static final String FHE_PATH             = "/fhe";
    static final String PRIVATE_KEY_PATH     = FHE_PATH + "/private";
    static final String SEARCH_PRIVATE       = FHE_PATH + "/searchprivate";
    static final String HASH                 = FHE_PATH + "/hash";

    static final String PUBLIC_KEY_PATH      = "/public";
    static final String RSA_PUBLIC           = "/rsa";
    static final String SALT_PATH            = "/salt";
    static final String CRYPTO_SERVICES_PATH = "/cryptoservice";

    static final String ID                   = Names.ID_FIELD;
    static final String USER_ID_PATH         = "/{" + ID + "}";
    static final String OBJECT_ID_PATH       = "/id/{" + ID + "}";
    static final String BULK_PATH            = "/bulk";

    @GET( CONTROLLER + PUBLIC_KEY_PATH + USER_ID_PATH )
    byte[] getPublicKey( @Path( ID ) UUID user);

    @POST( CONTROLLER + PUBLIC_KEY_PATH )
    void setPublicKey( @Body byte[] publicKey );

    @GET( CONTROLLER + PRIVATE_KEY_PATH )
    BlockCiphertext getPrivateKey();

    @POST( CONTROLLER + PRIVATE_KEY_PATH )
    void setPrivateKey( @Body BlockCiphertext encryptedPrivateKey );

    @GET( CONTROLLER + SALT_PATH + USER_ID_PATH )
    BlockCiphertext getEncryptedSalt( UUID user );

    @POST( CONTROLLER + SALT_PATH + USER_ID_PATH )
    void setEncryptedSalt( @Path( ID ) UUID user, @Body BlockCiphertext encryptedSalt);

    @GET( CONTROLLER + CRYPTO_SERVICES_PATH + OBJECT_ID_PATH )
    byte[] getObjectCryptoService( @Path( ID ) UUID objectId);

    @POST( CONTROLLER + CRYPTO_SERVICES_PATH + USER_ID_PATH + OBJECT_ID_PATH )
    void setObjectCryptoService( UUID user, UUID objectId, @Body byte[] crypto );

    @POST( CONTROLLER + PUBLIC_KEY_PATH + BULK_PATH )
    Map<UUID, byte[]> getPublicKeys( @Body Set<UUID> userIds );

    Map<UUID, byte[]> getObjectCryptoServices( @Body Set<UUID> ids );

    @POST( CONTROLLER + PRIVATE_KEY_PATH )
    Optional<String> setFHEPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE_KEY_PATH )
    Optional<BlockCiphertext> getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @POST( CONTROLLER + SEARCH_PRIVATE )
    Optional<String> setFHESearchPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + SEARCH_PRIVATE )
    Optional<BlockCiphertext> getFHESearchPrivateKeyForUser() throws BadRequestException;

    @POST( CONTROLLER + HASH )
    Response setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    /**
     * @return The byte level representation of the ClientHashFunction.
     * @throws BadRequestException
     */
    @GET( CONTROLLER + HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;
}