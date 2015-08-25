package com.kryptnostic.storage.v1.http;

import java.util.UUID;

import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface CryptoKeyStorageApi {
    final String CONTROLLER         = "/keys";
    final String PRIVATE            = "/private";
    final String PUBLIC             = "/public";
    final String HASH               = "/hash";
    final String ID                 = "id";

    public static final class PARAM {
        private PARAM() {}

        public static final String ID             = "/{" + Names.ID_FIELD + "}";
    }

    @PUT( CONTROLLER + PRIVATE + PARAM.ID )
    BasicResponse<String> setFHEPrivateKeyForUser( @Path( Names.ID_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE + PARAM.ID )
    byte[] getFHEPrivateKeyForUser( @Path( Names.ID_FIELD ) UUID id ) throws BadRequestException;

    @PUT( CONTROLLER + PUBLIC + PARAM.ID )
    BasicResponse<String> setFHEPublicKeyForUser( @Path( Names.ID_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PUBLIC + PARAM.ID )
    byte[] getFHEPublicKeyForUser( @Path( Names.ID_FIELD ) UUID id ) throws BadRequestException;

    @PUT( CONTROLLER + HASH + PARAM.ID )
    BasicResponse<String> setHashFunctionForUser( @Path( Names.ID_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + HASH + PARAM.ID )
    byte[] getHashFunctionForUser( @Path( Names.ID_FIELD ) UUID id ) throws BadRequestException;

}