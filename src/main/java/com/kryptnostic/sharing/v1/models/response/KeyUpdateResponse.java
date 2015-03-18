package com.kryptnostic.sharing.v1.models.response;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KeyUpdateResponse implements Serializable {
    private static final long serialVersionUID = 425136864440277920L;
    private final Set<String> ids;

    public KeyUpdateResponse( @JsonProperty( Names.ID_FIELD ) Set<String> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD )
    public Set<String> getIds() {
        return ids;
    }
}
