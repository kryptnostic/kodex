package com.kryptnostic.directory.v1.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class DocumentServiceRequest {

    private final List<String> ids;

    public DocumentServiceRequest( @JsonProperty( Names.ID_FIELD ) List<String> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD )
    public List<String> getIds() {
        return ids;
    }

}
