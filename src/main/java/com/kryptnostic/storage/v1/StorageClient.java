package com.kryptnostic.storage.v1;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.kryptnostic.kodex.v1.exceptions.types.BadRequestException;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceLockedException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.EncryptableBlock;
import com.kryptnostic.storage.v1.models.KryptnosticObject;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;
import com.kryptnostic.storage.v1.models.request.StorageRequest;
import com.kryptnostic.storage.v2.models.ObjectMetadata;

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
    Object getObject( UUID id ) throws ResourceNotFoundException;

    List<KryptnosticObject> getObjects( List<UUID> ids ) throws ResourceNotFoundException;

    /**
     * Push metadata to the service
     * 
     * @param metadata Properly formatted metadata
     * @throws BadRequestException The metadata was malformed or otherwise rejected by the server
     */
    void uploadMetadata( List<MetadataRequest> metadata ) throws BadRequestException;

    void deleteMetadata( UUID id );

    void deleteObject( UUID id );

    /**
     * Retrieve all the objectIds the current user has access to
     * 
     * @return Collection of objectIds
     */
    Collection<String> getObjectIds();

    Collection<String> getObjectIds( int offset, int pageSize );

    String uploadObject( StorageRequest req ) throws BadRequestException, SecurityConfigurationException,
            IrisException, ResourceLockedException, ResourceNotFoundException;

    List<EncryptableBlock> getObjectBlocks( UUID objectId, List<Integer> indices ) throws ResourceNotFoundException;

    Map<Integer, String> getObjectPreview( UUID objectId, List<Integer> locations, int wordRadius )
            throws SecurityConfigurationException, ExecutionException, ResourceNotFoundException,
            ClassNotFoundException, IOException;

    Collection<String> getObjectIdsByType( String type );

    Collection<String> getObjectIdsByType( String type, int offset, int pageSize );

    ObjectMetadata getObjectMetadata( UUID id ) throws ResourceNotFoundException;
}
