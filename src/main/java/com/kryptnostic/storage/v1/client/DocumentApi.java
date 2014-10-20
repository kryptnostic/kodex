package com.kryptnostic.storage.v1.client;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.request.DocumentCreationRequest;
import com.kryptnostic.storage.v1.models.request.DocumentFragmentRequest;
import com.kryptnostic.storage.v1.models.response.DocumentFragmentResponse;
import com.kryptnostic.storage.v1.models.response.DocumentResponse;

public interface DocumentApi {
    String DOCUMENT = "/document";
    String ID = "id";

    /**
     * Request a new document be created for later updating
     * 
     * @return The ID of the newly created document
     */
    @PUT(DOCUMENT)
    BasicResponse<String> createDocument(@Body DocumentCreationRequest request);

    /**
     * Update a document using a DocumentBlock
     * 
     * @param id
     *            Id of document to update
     * @param block
     *            A single block for the document
     * @return The progress and verification data for the updated document
     */
    @POST(DOCUMENT + "/{" + ID + "}")
    BasicResponse<String> updateDocument(@Path(ID) String documentId, @Body DocumentBlock block)
            throws ResourceNotFoundException;

    /**
     * Retrieve a document's text
     * 
     * @param id
     * @return DocumentResponse containing document
     */
    @GET(DOCUMENT + "/{" + ID + "}")
    DocumentResponse getDocument(@Path(ID) String documentId) throws ResourceNotFoundException;

    /**
     * 
     * @return Collection of document ids
     */
    @GET(DOCUMENT)
    BasicResponse<Collection<String>> getDocumentIds();

    /**
     * 
     * @param id
     *            Document identifier
     * @param offsets
     *            List of positions from which to obtain fragments
     * @param characterWindow
     *            Number of characters to return surrounding each offset
     * @return
     */
    @GET(DOCUMENT + "/{" + ID + "}/fragments")
    DocumentFragmentResponse getDocumentFragments(@Path(ID) String documentId, DocumentFragmentRequest request)
            throws ResourceNotFoundException;
}
