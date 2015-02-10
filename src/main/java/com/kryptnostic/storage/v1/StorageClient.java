package com.kryptnostic.storage.v1;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.EncryptableBlock;
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
    Document getDocument( String id ) throws ResourceNotFoundException;

    List<Document> getDocuments( List<String> ids ) throws ResourceNotFoundException;

    /**
     * Push metadata to the service
     * 
     * @param metadata Properly formatted metadata
     * @return Document identifier
     * @throws BadRequestException The metadata was malformed or otherwise rejected by the server
     */
    String uploadMetadata( MetadataRequest metadata ) throws BadRequestException;

    void deleteMetadata( String id );

    void deleteDocument( String id );

    /**
     * Retrieve all the documentIds the current user has access to
     * 
     * @return Collection of documentIds
     */
    Collection<String> getDocumentIds();

    Collection<String> getDocumentIds( int offset, int pageSize );

    String uploadDocument( StorageRequest req ) throws BadRequestException, SecurityConfigurationException,
            IrisException, ResourceLockedException, ResourceNotFoundException;

    List<EncryptableBlock> getDocumentBlocks( String id, List<Integer> indices ) throws ResourceNotFoundException;

    Map<Integer, String> getDocumentPreview( String documentId, List<Integer> locations, int wordRadius )
            throws SecurityConfigurationException, ExecutionException, ResourceNotFoundException;
}
