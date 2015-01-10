package com.kryptnostic.storage.v1.http;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotLockedException;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.sharing.v1.models.DocumentId;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.EncryptableBlock;
import com.kryptnostic.storage.v1.models.request.DocumentFragmentRequest;
import com.kryptnostic.storage.v1.models.response.DocumentFragmentResponse;
import com.kryptnostic.storage.v1.models.response.DocumentResponse;

public interface DocumentApi {
    String DOCUMENT         = "/document";
    String ID               = "id";
    String REALM            = "realm";
    String USER             = "user";
    String DOCUMENT_ID_PATH = "/{" + ID + "}";

    /**
     * Request a new document be created in a pending state
     * 
     * @return The ID of the newly created document
     * @throws BadRequestException Request was invalid
     */
    @PUT( DOCUMENT )
    BasicResponse<DocumentId> createPendingDocument() throws BadRequestException;

    /**
     * Request an existing document be put into a pending state
     * 
     * @param id
     * @param request
     * @return
     * @throws ResourceLockedException if the document is currently pending
     * @throws ResourceNotFoundException the document doesnt exist
     */
    @PUT( DOCUMENT + DOCUMENT_ID_PATH )
    BasicResponse<DocumentId> createPendingDocument( @Path( ID ) String id ) throws ResourceLockedException,
            ResourceNotFoundException;

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
    @POST( DOCUMENT + DOCUMENT_ID_PATH )
    BasicResponse<DocumentId> updateDocument( @Path( ID ) String id, @Body EncryptableBlock block )
            throws ResourceNotFoundException, ResourceNotLockedException, BadRequestException;

    /**
     * Retrieve a document's text
     * 
     * @param id
     * @return DocumentResponse containing document
     */
    @GET( DOCUMENT + DOCUMENT_ID_PATH )
    DocumentResponse getDocument( @Path( ID ) String id ) throws ResourceNotFoundException;

    @POST( DOCUMENT )
    BasicResponse<List<Document>> getDocuments( @Body List<DocumentId> docIds ) throws ResourceNotFoundException;

    /**
     * 
     * @return Collection of document ids
     */
    @GET( DOCUMENT )
    BasicResponse<Collection<DocumentId>> getDocumentIds();

    /**
     * @param id
     * @param request
     * @return
     * @throws ResourceNotFoundException
     */
    @POST( DOCUMENT + DOCUMENT_ID_PATH + "/fragments" )
    DocumentFragmentResponse getDocumentFragments( @Path( ID ) String id, @Body DocumentFragmentRequest request )
            throws ResourceNotFoundException;

    @DELETE( DOCUMENT + DOCUMENT_ID_PATH )
    BasicResponse<String> delete( @Path( ID ) String id );
}
