package com.kryptnostic.api.v1.models.request;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.bitwise.BitVectors;

/**
 * Search request for submittin
 * @author Matthew Tamayo-Rios <matthew@kryptnostic.com>
 */
public class SearchRequest {
    private static final String SEARCH_FUNCTION_PROPERTY = "SEARCH";
    private static final String MAX_RESULTS_PROPERTY = "MAX-RESULTS";
    private static final String PAGED_PROPERTY = "PAGED";
    
    private final BitVector searchToken;
    private final int maxResults; 
    private final boolean paged;
    
    @JsonCreator
    public SearchRequest( 
            @JsonProperty( SEARCH_FUNCTION_PROPERTY ) String searchToken, 
            @JsonProperty( MAX_RESULTS_PROPERTY ) Optional<Integer>  maxResults, 
            @JsonProperty( PAGED_PROPERTY ) Optional<Boolean> paged ) {
        this.searchToken = BitVectors.unmarshalBitvector( searchToken );
        this.maxResults = maxResults.or( 0 ); // 0 => unlimited
        this.paged = paged.or( false ); 
    }

    @JsonIgnore
    public BitVector getSearchToken() {
        return searchToken;
    }
    
    @JsonProperty( SEARCH_FUNCTION_PROPERTY ) 
    public String getSearchTokenAsString() {
        return BitVectors.marshalBitvector( searchToken );
    }

    @JsonProperty( MAX_RESULTS_PROPERTY ) 
    public int getMaxResults() {
        return maxResults;
    }

    @JsonProperty( PAGED_PROPERTY ) 
    public boolean isPaged() {
        return paged;
    }
}
