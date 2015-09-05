package com.kryptnostic.storage.v1.http;

import java.util.UUID;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CryptoKeyStorageApi {
    final String CONTROLLER         = "/keys";
    final String PRIVATE            = "/private";
    final String SEARCH_PRIVATE     = "/searchprivate";
    final String HASH               = "/hash";
    final String RSA_PUB            = "/rsapublic";
    final String ID                 = "id";
    final String ID_PATH            = "/{" + ID + "}";

    @POST( CONTROLLER + PRIVATE )
    BasicResponse<String> setFHEPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE )
    byte[] getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @POST( CONTROLLER + SEARCH_PRIVATE )
    BasicResponse<String> setFHESearchPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + SEARCH_PRIVATE )
    byte[] getFHESearchPriatveKeyForUser() throws BadRequestException;

    @POST( CONTROLLER + HASH )
    BasicResponse<String> setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;

    @GET( CONTROLLER + RSA_PUB )
    byte[] getRsaPublicKeyForUser( @Path( ID ) UUID userId ) throws BadRequestException;

    @POST( CONTROLLER + RSA_PUB )
    BasicResponse<String> setRsaPublicKey( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + RSA_PUB )
    byte[] getRsaPublicKeyForCurrentUser() throws BadRequestException;
}