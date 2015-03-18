package com.kryptnostic.search.v1.models.response;

import java.util.List;
import java.util.Map;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.search.v1.models.QueryHasherPairResult;
import com.kryptnostic.search.v1.models.SearchResult;

public class SearchResultResponse {

    private final List<SearchResult>                    data;
    private final Map<BitVector, QueryHasherPairResult> pairMap;
    private final int                                   offset;

    @JsonCreator
    public SearchResultResponse(
            @JsonProperty( Names.DATA_FIELD ) List<SearchResult> data,
            @JsonProperty( Names.PAIR_FIELD ) Map<BitVector, QueryHasherPairResult> pairMap,
            @JsonProperty( Names.OFFSET_FIELD ) int offset ) {
        this.data = data;
        this.offset = offset;
        this.pairMap = pairMap;
    }

    @JsonProperty( Names.DATA_FIELD )
    public List<SearchResult> getData() {
        return data;
    }

    @JsonProperty( Names.OFFSET_FIELD )
    public int getOffset() {
        return offset;
    }

    @JsonProperty( Names.PAIR_FIELD )
    public Map<BitVector, QueryHasherPairResult> getPairMap() {
        return pairMap;
    }
}
