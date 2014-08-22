package com.kryptnostic.api.v1.models.request;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.bitwise.BitVectors;

/**
 * Search request for submittin
 * 
 * @author Matthew Tamayo-Rios <matthew@kryptnostic.com>
 */
public class SearchRequest {
    private static final String SEARCH_FUNCTION_PROPERTY = "query";
    private static final String MAX_RESULTS_PROPERTY = "maxResults";
    private static final String OFFSET_PROPERTY = "offset";
    private static final String SORT_DATE_PROPERTY = "sortDate";
    private static final String SORT_SCORE_PROPERTY = "sortScore";

    public static final int DEFAULT_MAX_RESULTS = 30;

    private final BitVector searchToken;
    private final int maxResults;
    private final int offset;
    private final SortDirection sortDate;
    private final SortDirection sortScore;

    public enum SortDirection {
        DESC, ASC
    }

    @JsonCreator
    public SearchRequest(@JsonProperty(SEARCH_FUNCTION_PROPERTY) String searchToken,
            @JsonProperty(MAX_RESULTS_PROPERTY) Optional<Integer> maxResults,
            @JsonProperty(OFFSET_PROPERTY) Optional<Integer> offset,
            @JsonProperty(SORT_DATE_PROPERTY) Optional<SortDirection> sortDate,
            @JsonProperty(SORT_SCORE_PROPERTY) Optional<SortDirection> sortScore) {
        this.searchToken = BitVectors.unmarshalBitvector(searchToken);
        // 0 = unlimited
        this.maxResults = maxResults.or(DEFAULT_MAX_RESULTS);
        this.offset = offset.or(0);
        this.sortDate = sortDate.or(SortDirection.DESC);
        this.sortScore = sortDate.or(SortDirection.DESC);
    }

    @JsonProperty(SEARCH_FUNCTION_PROPERTY)
    public BitVector getSearchToken() {
        return searchToken;
    }

    @JsonProperty(MAX_RESULTS_PROPERTY)
    public int getMaxResults() {
        return maxResults;
    }

    @JsonProperty(OFFSET_PROPERTY)
    public int getOffset() {
        return offset;
    }

    @JsonProperty(SORT_DATE_PROPERTY)
    public SortDirection getSortDate() {
        return sortDate;
    }

    @JsonProperty(SORT_SCORE_PROPERTY)
    public SortDirection getSortScore() {
        return sortScore;
    }


}
