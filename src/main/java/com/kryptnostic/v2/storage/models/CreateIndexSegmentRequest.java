package com.kryptnostic.v2.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class CreateIndexSegmentRequest {
    private final byte[]              address;
    private final CreateObjectRequest createObjectRequest;

    @JsonCreator
    public CreateIndexSegmentRequest(
            @JsonProperty( Names.ADDRESS_FIELD ) byte[] address,
            @JsonProperty( Names.CREATE_OBJECT_REQUEST_FIELD ) CreateObjectRequest createObjectRequest ) {
        this.address = address;
        this.createObjectRequest = createObjectRequest;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public byte[] getAddress() {
        return address;
    }

    @JsonProperty( Names.CREATE_OBJECT_REQUEST_FIELD )
    public CreateObjectRequest getCreateObjectRequest() {
        return createObjectRequest;
    }

}
