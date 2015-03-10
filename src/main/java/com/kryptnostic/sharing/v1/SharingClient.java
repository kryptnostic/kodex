package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.util.Set;

import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.EncryptedSearchObjectKey;

public interface SharingClient {
    void unshareObjectWithUsers( String objectId, Set<UserKey> users );

    void shareObjectWithUsers( String uuid, Set<UserKey> users ) throws ResourceNotFoundException;

    int processIncomingShares() throws IOException, SecurityConfigurationException;

    int getIncomingSharesCount();

    EncryptedSearchObjectKey getObjectKey( String objectId ) throws ResourceNotFoundException;
}
