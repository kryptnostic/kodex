package com.kryptnostic.storage.v1.client;

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotLockedException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.request.DocumentCreationRequest;
import com.kryptnostic.storage.v1.models.request.DocumentFragmentRequest;
import com.kryptnostic.storage.v1.models.response.DocumentFragmentResponse;
import com.kryptnostic.storage.v1.models.response.DocumentResponse;

public interface DocumentApi {
    String DOCUMENT = "/document";
    String ID       = "id";

    /**
     * Request a new document be created in a pending state
     * 
     * @return The ID of the newly created document
     * @throws BadRequestException Request was invalid
     */
    @PUT( DOCUMENT )
    BasicResponse<DocumentId> createPendingDocument( @Body DocumentCreationRequest request ) throws BadRequestException;

    /**
     * Request an existing document be put into a pending state
     * 
     * @param documentId
     * @param request
     * @return
     * @throws ResourceLockedException if the document is currently pending
     * @throws ResourceNotFoundException the document doesnt exist
     */
    @PUT( DOCUMENT + "/{" + ID + "}" )
    BasicResponse<DocumentId> createPendingDocument(
            @Path( ID ) String documentId,
            @Body DocumentCreationRequest request ) throws ResourceLockedException, ResourceNotFoundException;

    /**
     * Update a document using a DocumentBlock
     * 
     * Precondition: Document is in a pending state
     * 
     * Postcondition: If this is the last block required to make the document valid, the document will be put into a
     * non-pending state and be available for reading
     * 
     * @param id Id of document to update
     * @param block A single block for the document
     * @return The progress and verification data for the updated document
     * @throws ResourceNotFoundException if specified documentId was not found
     * @throws ResourceNotLockedException if the specified document has not been locked for updating
     * @throws BadRequestException if the block is invalid
     */
    @POST( DOCUMENT + "/{" + ID + "}" )
    BasicResponse<DocumentId> updateDocument( @Path( ID ) String documentId, @Body DocumentBlock block )
            throws ResourceNotFoundException, ResourceNotLockedException, BadRequestException;

    /**
     * Retrieve a document's text
     * 
     * @param id
     * @return DocumentResponse containing document
     */
    @GET( DOCUMENT + "/{" + ID + "}" )
    DocumentResponse getDocument( @Path( ID ) String documentId ) throws ResourceNotFoundException;

    /**
     * 
     * @return Collection of document ids
     */
    @GET( DOCUMENT )
    BasicResponse<Collection<DocumentId>> getDocumentIds();

    /**
     * 
     * @param id Document identifier
     * @param offsets List of positions from which to obtain fragments
     * @param characterWindow Number of characters to return surrounding each offset
     * @return
     */
    @POST( DOCUMENT + "/{" + ID + "}/fragments" )
    DocumentFragmentResponse getDocumentFragments( @Path( ID ) String documentId, @Body DocumentFragmentRequest request )
            throws ResourceNotFoundException;
}
