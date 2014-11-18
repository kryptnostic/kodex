package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.search.v1.http.SearchApi;
import com.kryptnostic.sharing.v1.http.SharingApi;
import com.kryptnostic.storage.v1.http.DocumentApi;
import com.kryptnostic.storage.v1.http.MetadataApi;
import com.kryptnostic.storage.v1.http.SearchFunctionApi;

/**
 * This provides interfaces to Kryptnostic server-side services
 */
public interface KryptnosticServicesFactory {

    MetadataApi createMetadataApi();

    DocumentApi createDocumentApi();

    SearchApi createSearchApi();

    SearchFunctionApi createSearchFunctionApi();

    SharingApi createSharingApi();

    DirectoryApi createDirectoryApi();

}
