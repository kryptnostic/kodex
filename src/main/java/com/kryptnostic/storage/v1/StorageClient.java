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
import com.kryptnostic.storage.v1.models.EncryptableBlock;
import com.kryptnostic.storage.v1.models.KryptnosticObject;
import com.kryptnostic.storage.v1.models.ObjectMetadata;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;
import com.kryptnostic.storage.v1.models.request.StorageRequest;

/**
 * Defines a client for interacting with a Kryptnostic object storage server
 * 
 * @author sinaiman
 *
 */
public interface StorageClient {

    /**
     * Retrieve an object from the server
     * 
     * @param id The object id
     * @return KryptnosticObject of specified id
     * @throws ResourceNotFoundException The object with the specified ID was not found on the server
     */
    KryptnosticObject getObject( String id ) throws ResourceNotFoundException;

    List<KryptnosticObject> getObjects( List<String> ids ) throws ResourceNotFoundException;

    /**
     * Push metadata to the service
     * 
     * @param metadata Properly formatted metadata
     * @return Object id
     * @throws BadRequestException The metadata was malformed or otherwise rejected by the server
     */
    String uploadMetadata( MetadataRequest metadata ) throws BadRequestException;

    void deleteMetadata( String id );

    void deleteObject( String id );

    /**
     * Retrieve all the objectIds the current user has access to
     * 
     * @return Collection of objectIds
     */
    Collection<String> getObjectIds();

    Collection<String> getObjectIds( int offset, int pageSize );

    String uploadObject( StorageRequest req ) throws BadRequestException, SecurityConfigurationException,
            IrisException, ResourceLockedException, ResourceNotFoundException;

    List<EncryptableBlock> getObjectBlocks( String objectId, List<Integer> indices ) throws ResourceNotFoundException;

    Map<Integer, String> getObjectPreview( String objectId, List<Integer> locations, int wordRadius )
            throws SecurityConfigurationException, ExecutionException, ResourceNotFoundException;

    Collection<String> getObjectIdsByType( String type );

    Collection<String> getObjectIdsByType( String type, int offset, int pageSize );

    ObjectMetadata getObjectMetadata( String id ) throws ResourceNotFoundException;

    /**
     * @param objectMetadata
     * @param body
     * @return objectId
     * @throws SecurityConfigurationException
     * @throws ExecutionException
     * @throws ResourceNotFoundException
     * @throws IrisException
     * @throws BadRequestException
     */
    String appendObject( ObjectMetadata objectMetadata, String body ) throws SecurityConfigurationException,
            ExecutionException, ResourceNotFoundException, IrisException, BadRequestException;
}
