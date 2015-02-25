package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.util.Set;

import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.EncryptedSearchObjectKey;

public interface SharingClient {
    void unshareObjectWithUsers( String objectId, Set<UserKey> users );

    void shareObjectWithUsers( CryptoServiceLoader loader, String uuid, Set<UserKey> users );

    int processIncomingShares( CryptoServiceLoader loader ) throws IOException, SecurityConfigurationException;

    int getIncomingSharesCount();

    EncryptedSearchObjectKey getObjectKey( String objectId ) throws ResourceNotFoundException;
}
