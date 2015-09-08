package com.kryptnostic.storage.v1.http;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface CryptoKeyStorageApi {
    final String CONTROLLER         = "/keys";
    final String PRIVATE            = "/private";
    final String SEARCH_PRIVATE     = "/searchprivate";
    final String HASH               = "/hash";
    final String RSA_PUB            = "/rsapublic";
    final String ID                 = "id";
    final String ID_PATH            = "/{" + ID + "}";

    @POST( CONTROLLER + PRIVATE )
    Optional<String> setFHEPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE )
    byte[] getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @POST( CONTROLLER + SEARCH_PRIVATE )
    Optional<String> setFHESearchPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + SEARCH_PRIVATE )
    byte[] getFHESearchPrivateKeyForUser() throws BadRequestException;

    @POST( CONTROLLER + HASH )
    Optional<String> setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;

//
//    For future use!
//
//    @GET( CONTROLLER + RSA_PUB )
//    byte[] getRsaPublicKeyForUser( @Path( ID ) UUID userId ) throws BadRequestException;
//
//    @POST( CONTROLLER + RSA_PUB )
//    BasicResponse<String> setRsaPublicKey( @Body byte[] key ) throws BadRequestException;
//
//    @GET( CONTROLLER + RSA_PUB )
//    byte[] getRsaPublicKeyForCurrentUser() throws BadRequestException;
}