package com.kryptnostic.sharing.v1;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.Share;

public final class IncomingShares {

    private final Map<UUID, Share> shares;

    public IncomingShares() {
        shares = Maps.newHashMap();
    }

    @JsonCreator
    public IncomingShares( @JsonProperty( Names.SHARES_FIELD ) Map<UUID, Share> shares ) {
        this.shares = shares;
    }

    public void removeShares( Collection<UUID> sharesToRemove ) {
        shares.keySet().removeAll( sharesToRemove );
    }
    
    public void processSharingInformation( Share share ) {
        UUID id = UUID.randomUUID();
        while ( shares.containsKey( id ) ) {
            id = UUID.randomUUID();
        }
        shares.put( id, share );
    }
}
