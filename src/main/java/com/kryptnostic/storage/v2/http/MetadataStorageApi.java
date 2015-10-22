package com.kryptnostic.storage.v2.http;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.request.MetadataDeleteRequest;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

public interface MetadataStorageApi {
    String CONTROLLER = "/metadata";
    String OBJECT_ID = "objectId";
    String METADATA_ID = "metadataId";
    String DELETE   = "/delete";
    
    String OBJECT_ID_PATH = "/{" + OBJECT_ID + "}";
    String METADATA_ID_PATH = "/{" + METADATA_ID + "}";
    

    /**
     * Upload damn metaz
     * 
     * @param metadata
     * @return BasicResponse with empty body
     */
    @POST( CONTROLLER )
    BasicResponse<String> uploadMetadata( @Body MetadataRequest metadata ) throws BadRequestException;

    @PUT( CONTROLLER +)
    
    
    @POST( CONTROLLER + DELETE )
    BasicResponse<String> deleteAll( @Body MetadataDeleteRequest request );
}
