package com.kryptnostic.kodex.v1.client;

import java.io.IOException;
import java.util.Set;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.search.v1.SearchClient;
import com.kryptnostic.sharing.v1.SharingClient;
import com.kryptnostic.storage.v1.StorageClient;
import com.kryptnostic.users.v1.UserKey;

public interface KryptnosticClient extends SearchClient, StorageClient, SharingClient {
    public KryptnosticContext getContext();

    public Set<UserKey> listUserInRealm( String realm );
    public void processIncomingShares() throws IOException, SecurityConfigurationException;
}
