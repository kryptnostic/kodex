package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.kodex.v1.indexing.IndexingService;
import com.kryptnostic.kodex.v1.indexing.MetadataKeyService;
import com.kryptnostic.kodex.v1.security.SecurityService;
import com.kryptnostic.search.v1.client.SearchApi;
import com.kryptnostic.storage.v1.client.DocumentApi;
import com.kryptnostic.storage.v1.client.MetadataApi;
import com.kryptnostic.storage.v1.client.SearchFunctionApi;

/**
 * KryptonsticServicesFactory to decouple services used in Kryptnostic client interfaces from the Kodex.
 * 
 * @author Nick Hewitt
 */
public interface KryptnosticServicesFactory {

    MetadataApi createMetadataApi();

    DocumentApi createDocumentApi();

    SearchApi createSearchApi();

    MetadataKeyService createMetadataKeyService(KryptnosticContext context);

    IndexingService createIndexingService();

    SecurityService createSecurityService();

    SearchFunctionApi createSearchFunctionService();

}
