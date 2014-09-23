package com.kryptnostic.storage.v1;

import java.util.Collection;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

public interface StorageService {
    /**
     * Upload a document.
     * 
     * @param document
     * @return ID of newly saved document
     * @throws BadRequestException
     */
    String uploadDocument(String document) throws BadRequestException;

    /**
     * Update a document.
     * 
     * @param id
     * @param document
     * @return ID of newly saved document
     * @throws ResourceNotFoundException
     */
    String updateDocument(String id, String document) throws ResourceNotFoundException;

    /**
     * Retrieve a document's text.
     * 
     * @param id
     * @return document text
     * @throws ResourceNotFoundException
     */
    Document getDocument(String id) throws ResourceNotFoundException;
    
    /**
     * Push metadata to the service
     * @param metadata
     * @return
     * @throws BadRequestException 
     */
    String uploadMetadata(MetadataRequest metadata) throws BadRequestException;
    
    /**
     * 
     * @return Collection of documentIds
     */
    Collection<String> getDocumentIds();
}
