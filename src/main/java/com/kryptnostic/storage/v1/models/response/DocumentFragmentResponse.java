package com.kryptnostic.storage.v1.models.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.storage.v1.models.DocumentBlock;

public class DocumentFragmentResponse extends BasicResponse<Map<Integer, List<DocumentBlock>>> {

    @JsonCreator
    public DocumentFragmentResponse(@JsonProperty(DATA) Map<Integer, List<DocumentBlock>> fragments,
            @JsonProperty(STATUS) int status, @JsonProperty(SUCCESS) boolean success) {
        super(fragments, status, success);
    }
}
