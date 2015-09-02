package com.kryptnostic.storage.v1.http;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;

public interface CryptoKeyStorageApi {
    final String CONTROLLER         = "/keys";
    final String PRIVATE            = "/private";
    final String SEARCH_PRIVATE     = "/searchprivate";
    final String HASH               = "/hash";
    final String RSA_PUB            = "/rsapublic";
    final String ID                 = "id";

    @PUT( CONTROLLER + PRIVATE )
    BasicResponse<String> setFHEPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE )
    byte[] getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @PUT( CONTROLLER + SEARCH_PRIVATE )
    BasicResponse<String> setFHESearchPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + SEARCH_PRIVATE )
    byte[] getFHESearchPriatveKeyForUser() throws BadRequestException;

    @PUT( CONTROLLER + HASH )
    BasicResponse<String> setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;

    @PUT( CONTROLLER + RSA_PUB )
    BasicResponse<String> setRsaPublicKey( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + RSA_PUB )
    byte[] getRsaPublicKeyForCurrentUser() throws BadRequestException;
}