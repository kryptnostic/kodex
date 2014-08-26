package com.kryptnostic.api.v1.client;

import com.kryptnostic.api.v1.indexing.IndexingService;
import com.kryptnostic.api.v1.indexing.MetadataKeyService;

/**
 * KryptonsticServicesFactory to decouple services used in Kryptnostic
 * client interfaces from the Kodex.
 * 
 * @author Nick Hewitt
 */
public interface KryptnosticServicesFactory {
    /**
     * @return KryptnosticStorage
     */
    StorageAPI  createStorageService();
    /**
     * @return KryptnosticSearch
     */
    SearchAPI   createSearchService();
    /**
     * @return MetadataKeyService
     */
    MetadataKeyService  createMetadataKeyService();
    /**
     * @return IndexingService
     */
    IndexingService     createIndexingService();
}
