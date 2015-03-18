package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.directory.v1.DirectoryClient;
import com.kryptnostic.search.v1.SearchClient;
import com.kryptnostic.sharing.v1.SharingClient;
import com.kryptnostic.storage.v1.StorageClient;

public interface KryptnosticClient {
    KryptnosticContext getContext();

    KryptnosticConnection getConnection();

    DirectoryClient getDirectoryClient();

    SharingClient getSharingClient();

    SearchClient getSearchClient();

    StorageClient getStorageClient();
}
