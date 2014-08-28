package com.kryptnostic.search.v1.models.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.search.v1.models.SearchResult;

public class SearchResultResponse extends BasicResponse<Collection<SearchResult>> {

    @JsonCreator
    public SearchResultResponse(@JsonProperty(DATA) Collection<SearchResult> result, @JsonProperty(STATUS) int status,
            @JsonProperty(SUCCESS) boolean success) {
        super(result, status, success);
    }
}
