package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KeyUpdateResponse implements Serializable {
    private final Map<UUID,byte[]> ids;
    public KeyUpdateResponse( @JsonProperty(Names.ID_FIELD+"s") Map<UUID,byte[]> ids ) {
        this.ids = ids;
    }
    
    @JsonProperty(Names.ID_FIELD+"s") 
    public Map<UUID, byte[]> getIds() {
        return ids;
    }
}
