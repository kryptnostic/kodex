package com.kryptnostic.storage.v1;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;

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
    String getDocument(String id) throws ResourceNotFoundException;
}
