package com.kryptnostic.storage.v1.models.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.sharing.v1.DocumentId;

public class MetadataDeleteRequest {
    public final static String FIELD_METADATA = "metadata";

    private List<DocumentId>   ids;

    public MetadataDeleteRequest( @JsonProperty( FIELD_METADATA ) List<DocumentId> ids ) {
        this.ids = ids;
    }

    @JsonProperty( FIELD_METADATA )
    public List<DocumentId> getIds() {
        return ids;
    }

}
