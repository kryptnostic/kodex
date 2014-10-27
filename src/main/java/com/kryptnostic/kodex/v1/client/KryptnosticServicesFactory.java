package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.directory.v1.KeyApi;
import com.kryptnostic.directory.v1.UsersApi;
import com.kryptnostic.search.v1.client.SearchApi;
import com.kryptnostic.sharing.v1.requests.SharingApi;
import com.kryptnostic.storage.v1.client.DocumentApi;
import com.kryptnostic.storage.v1.client.MetadataApi;
import com.kryptnostic.storage.v1.client.SearchFunctionApi;

/**
 * This provides interfaces to Kryptnostic server-side services
 */
public interface KryptnosticServicesFactory {

    MetadataApi createMetadataApi();

    DocumentApi createDocumentApi();

    SearchApi createSearchApi();

    SearchFunctionApi createSearchFunctionApi();

    SharingApi createSharingApi();

    KeyApi createKeyApi();

    UsersApi createUsersApi();
}
