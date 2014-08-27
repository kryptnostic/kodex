package com.kryptnostic.api.v1.client;

import com.kryptnostic.api.v1.client.web.DocumentApi;
import com.kryptnostic.api.v1.client.web.MetadataApi;
import com.kryptnostic.api.v1.client.web.SearchApi;
import com.kryptnostic.api.v1.indexing.IndexingService;
import com.kryptnostic.api.v1.indexing.MetadataKeyService;

/**
 * KryptonsticServicesFactory to decouple services used in Kryptnostic client interfaces from the Kodex.
 * 
 * @author Nick Hewitt
 */
public interface KryptnosticServicesFactory {

    MetadataApi createMetadataApi();

    DocumentApi createDocumentApi();

    SearchApi createSearchApi();

    MetadataKeyService createMetadataKeyService();

    IndexingService createIndexingService();
}
