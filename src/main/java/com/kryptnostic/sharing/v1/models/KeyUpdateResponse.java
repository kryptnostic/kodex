package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.DocumentId;

public final class KeyUpdateResponse implements Serializable {
    private static final long     serialVersionUID = 425136864440277920L;
    private final Set<DocumentId> ids;

    public KeyUpdateResponse( @JsonProperty( Names.ID_FIELD + "s" ) Set<DocumentId> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD + "s" )
    public Set<DocumentId> getIds() {
        return ids;
    }
}
