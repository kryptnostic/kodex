package com.kryptnostic.storage.v1.http;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.model.ByteArrayEnvelope;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;

public interface CryptoKeyStorageApi {
    String CONTROLLER     = "/keys";
    String PRIVATE        = "/private";
    String SEARCH_PRIVATE = "/searchprivate";
    String HASH           = "/hash";

    @POST( CONTROLLER + PRIVATE )
    Optional<String> setFHEPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE )
    Optional<BlockCiphertext> getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @POST( CONTROLLER + SEARCH_PRIVATE )
    Optional<String> setFHESearchPrivateKeyForCurrentUser( @Body BlockCiphertext key ) throws BadRequestException;

    @GET( CONTROLLER + SEARCH_PRIVATE )
    Optional<BlockCiphertext> getFHESearchPrivateKeyForUser() throws BadRequestException;

    @POST( CONTROLLER + HASH )
    Optional<String> setHashFunctionForCurrentUser( @Body ByteArrayEnvelope key ) throws BadRequestException;

    /**
     * @return The byte level representation of the ClientHashFunction.
     * @throws BadRequestException
     */
    @GET( CONTROLLER + HASH )
    Optional<ByteArrayEnvelope> getHashFunctionForCurrentUser() throws BadRequestException;
}