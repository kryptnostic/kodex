package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.search.v1.http.SearchApi;
import com.kryptnostic.sharing.v1.http.SharingApi;
import com.kryptnostic.storage.v1.http.ObjectStorageApi;
import com.kryptnostic.storage.v1.http.MetadataStorageApi;
import com.kryptnostic.storage.v1.http.SearchFunctionStorageApi;

/**
 * This provides interfaces to Kryptnostic server-side services
 */
public interface KryptnosticServicesFactory {

    MetadataStorageApi createMetadataApi();

    ObjectStorageApi createDocumentApi();

    SearchApi createSearchApi();

    SearchFunctionStorageApi createSearchFunctionApi();

    SharingApi createSharingApi();

    DirectoryApi createDirectoryApi();

}
