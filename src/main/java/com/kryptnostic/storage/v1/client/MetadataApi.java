package com.kryptnostic.storage.v1.client;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

public interface MetadataApi {
    String METADATA = "/metadata";

    /**
     * Upload damn metaz
     * 
     * @param metadata
     * @return BasicResponse with empty body
     */
    @POST(METADATA)
    BasicResponse<String> uploadMetadata(@Body MetadataRequest metadata) throws BadRequestException;
}
