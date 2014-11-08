package com.kryptnostic.storage.v1;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

/**
 * Defines a client for interacting with a Kryptnostic document storage server
 * 
 * @author sinaiman
 *
 */
public interface StorageClient {
    /**
     * Upload a document. Also indexes the document, resulting in the generation and upload of document metadata
     * 
     * @param document
     * @return ID of newly saved document
     * @throws BadRequestException The document was rejected by the server
     * @throws IrisException An error occurred in the Kryptnostic Client
     * @throws SecurityConfigurationException The information required to encrypt the document was invalid
     */
    String uploadDocumentWithMetadata( String document ) throws BadRequestException, IrisException,
            SecurityConfigurationException;

    /**
     * Upload a document body without performing an index or uploading search metadata to the server.
     * 
     * @param document
     * @return ID of newly saved document
     * @throws BadRequestException The document was rejected by the server
     * @throws IrisException An error occurred in the Kryptnostic Client
     * @throws SecurityConfigurationException The information required to encrypt the document was invalid
     */
    String uploadDocumentWithoutMetadata( String document ) throws BadRequestException, IrisException,
            SecurityConfigurationException;

    /**
     * Update the document
     * 
     * @param id The string ID of the document to be updated
     * @param documentBody The text to update the document with
     * @return ID of newly saved document
     * @throws BadRequestException The document was rejected by the server
     * @throws IrisException An error occurred in the Kryptnostic Client
     * @throws ResourceNotFoundException The document with the specified ID was not found on the server
     * @throws ResourceLockedException The document that was attempted to be updated has been locked by another process.
     *             The particular document is currently pending an update
     * @throws SecurityConfigurationException The information required to encrypt the document was invalid
     */
    String updateDocumentWithMetadata( String id, String documentBody ) throws BadRequestException,
            ResourceNotFoundException, ResourceLockedException, SecurityConfigurationException, IrisException;

    /**
     * Uploads the document without indexing it
     * 
     * @param id The string ID of the document to be updated
     * @param documentBody The text to update the document with
     * @return Id of the newly saved document
     * @throws BadRequestException The document was rejected by the server
     * @throws IrisException An error occurred in the Kryptnostic Client
     * @throws ResourceNotFoundException The document with the specified ID was not found on the server
     * @throws ResourceLockedException The document that was attempted to be updated has been locked by another process.
     *             The particular document is currently pending an update
     * @throws SecurityConfigurationException The information required to encrypt the document was invalid
     */

    String updateDocumentWithoutMetadata( String id, String documentBody ) throws BadRequestException,
            ResourceNotFoundException, ResourceLockedException, SecurityConfigurationException, IrisException;

    /**
     * Retrieve a document from the server
     * 
     * @param id The document Id, scoped to the user it belongs to
     * @return document Document object
     * @throws ResourceNotFoundException The document with the specified ID was not found on the server
     */
    Document getDocument( DocumentId id ) throws ResourceNotFoundException;

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
    Map<Integer, String> getDocumentFragments( DocumentId id, List<Integer> offsets, int characterWindow )
            throws ResourceNotFoundException, SecurityConfigurationException, IrisException;
}
