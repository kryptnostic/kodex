package com.kryptnostic.search.v1.models.request;

import java.util.Arrays;
import java.util.List;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.search.v1.models.QueryHasherPairResult;

/**
 * Search request for submittin
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public class SearchRequest {
    public static final int                   DEFAULT_MAX_RESULTS = 30;

    private final List<BitVector>             searchToken;
    private final List<QueryHasherPairResult> pairResults;
    private final int                         maxResults;
    private final int                         offset;

    public SearchRequest( List<BitVector> searchToken ) {
        this( searchToken, DEFAULT_MAX_RESULTS, null, 0 );
    }

    @JsonCreator
    public SearchRequest(
            @JsonProperty( Names.QUERY_FIELD ) List<BitVector> searchToken,
            @JsonProperty( Names.MAX_FIELD ) int maxResults,
            @JsonProperty( Names.PAIR_FIELD ) List<QueryHasherPairResult> pairResults,
            @JsonProperty( Names.OFFSET_FIELD ) int offset ) {
        // Preconditions.checkArgument( searchToken != null && pairResults != null
        // && searchToken.size() == pairResults.size() );
        this.searchToken = searchToken;
        this.pairResults = pairResults;
        this.maxResults = maxResults;
        this.offset = offset;
    }

    public static SearchRequest searchToken( BitVector searchToken ) {
        return SearchRequest.searchToken( Arrays.asList( searchToken ) );
    }

    public static SearchRequest searchToken( List<BitVector> searchTokens ) {
        return new SearchRequest( searchTokens );
    }

    public static SearchRequest searchToken( List<BitVector> searchTokens, int maxResults ) {
        return new SearchRequest( searchTokens, maxResults, null, 0 );
    }

    public static SearchRequest searchToken(
            List<BitVector> searchTokens,
            int maxResults,
            List<QueryHasherPairResult> pairResults,
            int offset ) {
        return new SearchRequest( searchTokens, maxResults, pairResults, offset );
    }

    @JsonProperty( Names.QUERY_FIELD )
    public List<BitVector> getSearchToken() {
        return searchToken;
    }

    @JsonProperty( Names.MAX_FIELD )
    public int getMaxResults() {
        return maxResults;
    }

    @JsonProperty( Names.PAIR_FIELD )
    public List<QueryHasherPairResult> getPairResults() {
        return pairResults;
    }

    @JsonProperty( Names.OFFSET_FIELD )
    public int getOffset() {
        return offset;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof SearchRequest ) ) {
            return false;
        }
        SearchRequest other = (SearchRequest) obj;
        if ( searchToken == null ) {
            if ( other.searchToken != null ) {
                return false;
            }
        } else if ( !searchToken.equals( other.searchToken ) ) {
            return false;
        }
        if ( pairResults == null ) {
            if ( other.pairResults != null ) {
                return false;
            }
        } else if ( !pairResults.equals( other.pairResults ) ) {
            return false;
        }

        return offset == other.offset && maxResults == other.maxResults;
    }

}
