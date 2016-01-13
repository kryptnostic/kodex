package com.kryptnostic.search.v1.models.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.search.v1.models.SearchResult;

public class SearchResultResponse {

    private final List<SearchResult> data;
    private final int                offset;

    @JsonCreator
    public SearchResultResponse(
            @JsonProperty( Names.DATA_FIELD ) List<SearchResult> data,
            @JsonProperty( Names.OFFSET_FIELD ) int offset) {
        this.data = data;
        this.offset = offset;
    }

    @JsonProperty( Names.DATA_FIELD )
    public List<SearchResult> getData() {
        return data;
    }

    @JsonProperty( Names.OFFSET_FIELD )
    public int getOffset() {
        return offset;
    }

}
