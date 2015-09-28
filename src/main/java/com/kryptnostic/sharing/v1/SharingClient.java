package com.kryptnostic.sharing.v1;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public interface SharingClient {
    void unshareObjectWithUsers( String objectId, Set<UUID> users );

    Set<String> processIncomingShares() throws IOException, SecurityConfigurationException;

    int getIncomingSharesCount();

    void shareObjectWithUsers( String objectId, Set<UUID> users ) throws ResourceNotFoundException;

    Optional<byte[]> getSearchPair( String objectId ) throws ResourceNotFoundException;

    Optional<byte[]> getSharingPair( String objectId ) throws ResourceNotFoundException;

    void shareObjectWithUsers( String objectId, Set<UUID> users, Optional<byte[]> sharingPair )
            throws ResourceNotFoundException;
}
