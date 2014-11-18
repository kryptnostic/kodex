package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.util.Set;

import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.sharing.v1.models.DocumentId;

public interface SharingClient {
    void unsharedDocumentWithUsers( DocumentId documentId, Set<UserKey> users );
    void shareDocumentWithUsers( DocumentId documentId, Set<UserKey> users );
    int processIncomingShares() throws IOException, SecurityConfigurationException;
}
