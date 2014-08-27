package com.kryptnostic.storage.v1.client;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.request.DocumentRequest;
import com.kryptnostic.storage.v1.models.response.DocumentResponse;

public interface DocumentApi {
    String DOCUMENT = "/document";
    String ID = "id";

    /**
     * Upload a document
     * 
     * @param document
     * @return The ID of the newly saved document
     */
    @POST(DOCUMENT)
    BasicResponse<String> uploadDocument(@Body DocumentRequest document) throws BadRequestException;

    /**
     * Update a document
     * 
     * @param document
     * @return The ID of the newly saved document
     */
    @POST(DOCUMENT + "/{" + ID + "}")
    BasicResponse<String> updateDocument(@Path(ID) String id, @Body DocumentRequest document)
            throws ResourceNotFoundException;

    /**
     * Retrieve a document's text
     * 
     * @param id
     * @return DocumentResponse containing document
     */
    @GET(DOCUMENT + "/{" + ID + "}")
    DocumentResponse getDocument(@Path(ID) String id) throws ResourceNotFoundException;
}
