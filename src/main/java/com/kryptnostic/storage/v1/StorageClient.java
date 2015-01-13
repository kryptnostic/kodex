package com.kryptnostic.storage.v1;

import java.util.Collection;
import java.util.List;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.sharing.v1.models.DocumentId;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;
import com.kryptnostic.storage.v1.models.request.StorageRequest;

/**
 * Defines a client for interacting with a Kryptnostic document storage server
 * 
 * @author sinaiman
 *
 */
public interface StorageClient {

    /**
     * Retrieve a document from the server
     * 
     * @param id The document Id, scoped to the user it belongs to
     * @return document Document object
     * @throws ResourceNotFoundException The document with the specified ID was not found on the server
     */
    Document getDocument( DocumentId id ) throws ResourceNotFoundException;

    List<Document> getDocuments( List<DocumentId> ids ) throws ResourceNotFoundException;

    /**
     * Push metadata to the service
     * 
     * @param metadata Properly formatted metadata
     * @return Document identifier
     * @throws BadRequestException The metadata was malformed or otherwise rejected by the server
     */
    String uploadMetadata( MetadataRequest metadata ) throws BadRequestException;

    void deleteMetadata( DocumentId id );

    void deleteDocument( DocumentId id );

    /**
     * Retrieve all the documentIds the current user has access to
     * 
     * @return Collection of documentIds
     */
    Collection<DocumentId> getDocumentIds();

    String uploadDocument( StorageRequest req ) throws BadRequestException, SecurityConfigurationException,
            IrisException, ResourceLockedException, ResourceNotFoundException;

    /**
     * Retrieve fragments of a document as specified by a list of character offsets
     * 
     * @param id Id of the document, scoped to the owner of the document
     * @param offsets List of character-count offsets that correspond to a token in a document
     * @param characterWindow The radius of text to return for each offset
     * @return
     * @throws IrisException An error occurred in the Kryptnostic Client
     * @throws ResourceNotFoundException The document with the specified ID was not found on the server
     * @throws SecurityConfigurationException The information required to encrypt the document was invalid
     */
    // Map<Integer, String> getDocumentFragments( DocumentId id, List<Integer> offsets, int characterWindow )
    // throws ResourceNotFoundException, SecurityConfigurationException, IrisException;
}
