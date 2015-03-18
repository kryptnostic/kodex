package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.ObjectMetadata;

public class PendingObjectRequest {
    private final String type;

    @JsonIgnore
    public PendingObjectRequest() {
        this( ObjectMetadata.DEFAULT_TYPE );
    }

    @JsonCreator
    public PendingObjectRequest( @JsonProperty( Names.TYPE_FIELD ) String type ) {
        this.type = type;
    }

    @JsonProperty( Names.TYPE_FIELD )
    public String getType() {
        return type;
    }
}
