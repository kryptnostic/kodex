package com.kryptnostic.storage.v1.models.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class MetadataDeleteRequest {
    private List<String> ids;

    public MetadataDeleteRequest( @JsonProperty( Names.ID_FIELD ) List<String> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD )
    public List<String> getIds() {
        return ids;
    }

}
