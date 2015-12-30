package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public interface SharingClient {
    void unshareObjectWithUsers( VersionedObjectKey objectId, Set<UUID> users );

    Set<VersionedObjectKey> processIncomingShares() throws IOException, SecurityConfigurationException;

    int getIncomingSharesCount();

    void shareObjectWithUsers( VersionedObjectKey objectId, Set<UUID> users ) throws ResourceNotFoundException;

    Optional<byte[]> getSearchPair( VersionedObjectKey objectId ) throws ResourceNotFoundException;

    Optional<byte[]> getSharingPair( VersionedObjectKey objectId ) throws ResourceNotFoundException;

    void shareObjectWithUsers( VersionedObjectKey objectId, Set<UUID> users, Optional<byte[]> sharingPair )
            throws ResourceNotFoundException;
}
