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
    final String HASH               = "/public";
    final String ID                 = "id";

    public static final class PARAM {
        private PARAM() {}

        public static final String USER           = "/{" + Names.USER_FIELD + "}";
        public static final String ID             = "/{" + Names.ID_FIELD + "}";
    }

    @PUT( CONTROLLER + PRIVATE + PARAM.USER )
    BasicResponse<String> addFHEPrivateKeyForUser( @Path( Names.USER_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PRIVATE + PARAM.USER )
    byte[] getFHEPrivateKeyForUser( @Path( Names.USER_FIELD ) UUID id ) throws BadRequestException;

    @PUT( CONTROLLER + PUBLIC + PARAM.USER )
    BasicResponse<String> addFHEPublicKeyForUser( @Path( Names.USER_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PUBLIC + PARAM.USER )
    byte[] getFHEPublicKeyForUser( @Path( Names.USER_FIELD ) UUID id ) throws BadRequestException;

    @PUT( CONTROLLER + PUBLIC + PARAM.USER )
    BasicResponse<String> addHashFunctionForUser( @Path( Names.USER_FIELD ) UUID id, @Body byte[] key ) throws BadRequestException;

    @GET( CONTROLLER + PUBLIC + PARAM.USER )
    byte[] getHashFunctionForUser( @Path( Names.USER_FIELD ) UUID id ) throws BadRequestException;

}