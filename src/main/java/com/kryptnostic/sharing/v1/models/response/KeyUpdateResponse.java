package com.kryptnostic.sharing.v1.models.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KeyUpdateResponse  {
    private final Set<String> ids;

    public KeyUpdateResponse( @JsonProperty( Names.ID_FIELD ) Set<String> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD )
    public Set<String> getIds() {
        return ids;
    }
}
