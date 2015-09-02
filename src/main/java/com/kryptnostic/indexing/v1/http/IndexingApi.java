package com.kryptnostic.indexing.v1.http;

import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

import com.google.common.base.Optional;

public interface IndexingApi {

    public static final String CONTROLLER     = "/indexing";
    public static final String ADDRESS        = "/address";
    public static final String SHARING        = "/share";
    public static final String METADATA       = "/metadata";
    public static final String ID             = "id";
    public static final String OBJECT_ID_PATH = "/{" + ID + "}";

    @POST( CONTROLLER + OBJECT_ID_PATH )
    Optional<String> storeIndex( @Path( ID ) UUID document, @Body byte[] metadata );

    @POST( CONTROLLER + ADDRESS + OBJECT_ID_PATH )
    Optional<String> storeObjectAddrMatrix( @Path( ID ) UUID document, @Body byte[] objAddrMatrix );

    @POST( CONTROLLER + SHARING + OBJECT_ID_PATH )
    Optional<String> storeSharingPair( @Path( ID ) UUID document, @Body byte[] sharingPair );

    @POST( CONTROLLER + METADATA )
    Optional<String> storeMetadataForIndexAddress( @Body byte[] addressMetadataPair );
}
