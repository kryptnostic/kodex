package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.search.v1.SearchClient;
import com.kryptnostic.storage.v1.StorageClient;

public interface KryptnosticClient extends SearchClient, StorageClient {
    public KryptnosticContext getContext();
}
