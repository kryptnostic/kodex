package com.kryptnostic.directory.v1.models.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class DocumentServiceResponse {

    private final Map<String, byte[]> data;

    public DocumentServiceResponse( @JsonProperty( Names.DATA_FIELD ) Map<String, byte[]> data ) {
        super();
        this.data = data;
    }

    @JsonProperty( Names.DATA_FIELD )
    public Map<String, byte[]> getData() {
        return data;
    }

}
