package com.kryptnostic.v2.search;

import java.util.SortedSet;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class SearchResultResponse {
    private final UUID                    queryId;
    private final SortedSet<SearchResult> results;

    @JsonCreator
    public SearchResultResponse(
            @JsonProperty( Names.RESULTS_FIELD ) SortedSet<SearchResult> results,
            @JsonProperty( Names.QUERY_FIELD ) UUID queryId) {
        this.queryId = queryId;
        this.results = results;
    }

    @JsonProperty( Names.RESULTS_FIELD )
    public SortedSet<SearchResult> getResults() {
        return results;
    }

    @JsonProperty( Names.QUERY_FIELD )
    public UUID getQueryId() {
        return queryId;
    }

}
