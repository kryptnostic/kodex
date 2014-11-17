package com.kryptnostic.sharing.v1;

import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.Maps;
import com.kryptnostic.sharing.v1.models.Share;

public final class IncomingShares extends HashSet<Share> {
    private static final long serialVersionUID = -4163211276862529808L;

    public Map<DocumentId, Share> asMap() {
        Map<DocumentId, Share> sharingMap = Maps.newHashMap();
        for ( Share s : this ) {
            sharingMap.put( s.getDocumentId(), s );
        }
        return sharingMap;
    }
}
