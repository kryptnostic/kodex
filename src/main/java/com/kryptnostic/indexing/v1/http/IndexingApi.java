package com.kryptnostic.indexing.v1.http;

import java.util.Set;
import java.util.UUID;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.google.common.base.Optional;

public interface IndexingApi {

    public static final String CONTROLLER     = "/indexing";
    public static final String INDEX_PAIR     = "/indexPair";
    public static final String ADDRESS        = "/address";
    public static final String SHARING        = "/share";
    public static final String METADATA       = "/metadata";
    public static final String ID             = "id";
    public static final String OBJECT_ID_PATH = "/{" + ID + "}";

    @POST( CONTROLLER + OBJECT_ID_PATH )
    Optional<String> storeObjectMetadata( @Path( ID ) UUID documentId, @Body byte[] metadata );

    @GET( CONTROLLER + OBJECT_ID_PATH )
    Set<byte[]> getObjectMetadata( @Path( ID ) UUID documentId );

    @POST( CONTROLLER + ADDRESS + OBJECT_ID_PATH )
    Optional<String> storeObjectAddrMatrix( @Path( ID ) UUID documentId, @Body byte[] objAddrMatrix );

    @GET( CONTROLLER + ADDRESS + OBJECT_ID_PATH )
    byte[] getObjectAddrMatrix( @Path( ID ) UUID documentId );

    @POST( CONTROLLER + SHARING + OBJECT_ID_PATH )
    Optional<String> storeSharingPair( @Path( ID ) UUID documentId, @Body byte[] sharingPair );

    @GET( CONTROLLER + SHARING + OBJECT_ID_PATH )
    byte[] getSharingPair( @Path( ID ) UUID documentId );

    @POST( CONTROLLER + SHARING + OBJECT_ID_PATH )
    Optional<String> storeIndexPair( @Path( ID ) UUID documentId, @Body byte[] indexPair );

    @GET( CONTROLLER + SHARING + OBJECT_ID_PATH )
    byte[] getIndexPair( @Path( ID ) UUID documentId );

    @POST( CONTROLLER + METADATA )
    Optional<String> storeIndexMetadataForAddress( @Body byte[] addressMetadataPair );

    @GET( CONTROLLER + METADATA )
    byte[] getIndexMetadataForAddress( byte[] address );

}
