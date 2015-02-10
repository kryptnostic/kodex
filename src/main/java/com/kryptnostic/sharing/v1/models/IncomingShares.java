package com.kryptnostic.sharing.v1.models;

import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.Maps;

public final class IncomingShares extends HashSet<Share> {
    private static final long serialVersionUID = -4163211276862529808L;

    public Map<String, Share> asMap() {
        Map<String, Share> sharingMap = Maps.newHashMap();
        for ( Share s : this ) {
            sharingMap.put( s.getDocumentId(), s );
        }
        return sharingMap;
    }
}
