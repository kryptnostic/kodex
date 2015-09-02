package com.kryptnostic.storage.v1.http;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;

public interface CryptoKeyStorageApi {
    final String CONTROLLER         = "/keys";
    final String PRIVATE            = "/private";
    final String PUBLIC             = "/public";
    final String HASH               = "/hash";
    final String ID                 = "id";

    @PUT( CONTROLLER + PRIVATE )
    BasicResponse<String> setFHEPrivateKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE )
    byte[] getFHEPrivateKeyForCurrentUser() throws BadRequestException;

    @PUT( CONTROLLER + PUBLIC )
    BasicResponse<String> setFHEPublicKeyForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PUBLIC )
    byte[] getFHEPublicKeyForUser() throws BadRequestException;

    @PUT( CONTROLLER + HASH )
    BasicResponse<String> setHashFunctionForCurrentUser( @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + HASH )
    byte[] getHashFunctionForCurrentUser() throws BadRequestException;

}