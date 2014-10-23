package com.kryptnostic.storage.v1;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

public interface StorageService {
    /**
     * Upload a document. Also indexes the document, resulting in the generation and upload of document metadata
     * 
     * @param document
     * @return ID of newly saved document
     * @throws BadRequestException
     * @throws IOException 
     * @throws SecurityConfigurationException 
     * @throws ResourceNotFoundException 
     * @throws ClassNotFoundException 
     */
    String uploadDocument(String document) throws BadRequestException, SecurityConfigurationException, IOException, ResourceNotFoundException, ClassNotFoundException;
    
    /**
     * Simply uploads the document without indexing it
     * @param document
     * @return Id of the newly saved document
     * @throws BadRequestException
     * @throws IOException 
     * @throws SecurityConfigurationException 
     * @throws ClassNotFoundException 
     */
    
    String updateDocumentWithoutMetadata(String id, String document) throws BadRequestException, SecurityConfigurationException, IOException, ClassNotFoundException;
    
    String uploadDocumentWithoutMetadata(String document) throws BadRequestException, SecurityConfigurationException,
            IOException, ClassNotFoundException;

    /**
     * Update a document.
     * 
     * @param id
     * @param document
     * @return ID of newly saved document
     * @throws ResourceNotFoundException
     * @throws IOException 
     * @throws SecurityConfigurationException 
     * @throws BadRequestException 
     * @throws ClassNotFoundException 
     */
    String updateDocument(String id, String document) throws ResourceNotFoundException, BadRequestException, SecurityConfigurationException, IOException, ClassNotFoundException;

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
    
    /**
     * 
     * @param id
     * @param offsets
     * @param characterWindow
     * @return
     * @throws ResourceNotFoundException
     * @throws SecurityConfigurationException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    Map<Integer, String> getDocumentFragments(String id, List<Integer> offsets, int characterWindow) throws ResourceNotFoundException, JsonParseException, JsonMappingException, IOException, ClassNotFoundException, SecurityConfigurationException;
}
