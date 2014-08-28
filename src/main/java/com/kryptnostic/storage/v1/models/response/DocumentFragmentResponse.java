package com.kryptnostic.storage.v1.models.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;

public class DocumentFragmentResponse extends BasicResponse<Map<Integer, String>> {

    @JsonCreator
    public DocumentFragmentResponse(@JsonProperty(DATA) Map<Integer, String> fragments,
            @JsonProperty(STATUS) int status, @JsonProperty(SUCCESS) boolean success) {
        super(fragments, status, success);
    }
}