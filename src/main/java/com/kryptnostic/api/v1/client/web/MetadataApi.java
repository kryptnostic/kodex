package com.kryptnostic.api.v1.client.web;

import retrofit.http.Body;
import retrofit.http.POST;

import com.kryptnostic.api.v1.exceptions.types.BadRequestException;
import com.kryptnostic.api.v1.models.request.MetadataRequest;
import com.kryptnostic.api.v1.models.response.BasicResponse;

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
