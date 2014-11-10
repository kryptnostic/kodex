package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.DocumentId;

public final class KeyUpdateResponse implements Serializable {
    private static final long serialVersionUID = 425136864440277920L;
    private final Map<UUID,DocumentId> ids;
    public KeyUpdateResponse( @JsonProperty(Names.ID_FIELD+"s") Map<UUID,DocumentId> ids ) {
        this.ids = ids;
    }
    
    @JsonProperty(Names.ID_FIELD+"s") 
    public Map<UUID, DocumentId> getIds() {
        return ids;
    }
}
