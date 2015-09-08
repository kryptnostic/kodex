package com.kryptnostic.search.v1.models.request;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Search request for submittin
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public class SearchRequest {
    public static final int    DEFAULT_MAX_RESULTS = 30;

    private final List<byte[]> searchToken;
    private final int          maxResults;
    private final int          offset;

    public SearchRequest( List<byte[]> searchToken ) {
        this( searchToken, DEFAULT_MAX_RESULTS, 0 );
    }

    @JsonCreator
    public SearchRequest(
            @JsonProperty( Names.QUERY_FIELD ) List<byte[]> searchToken,
            @JsonProperty( Names.MAX_FIELD ) int maxResults,
            @JsonProperty( Names.OFFSET_FIELD ) int offset ) {
        // Preconditions.checkArgument( searchToken != null && pairResults != null
        // && searchToken.size() == pairResults.size() );
        this.searchToken = searchToken;
        this.maxResults = maxResults;
        this.offset = offset;
    }

    public static SearchRequest searchToken( byte[] searchToken ) {
        return SearchRequest.searchToken( Arrays.asList( searchToken ) );
    }

    public static SearchRequest searchToken( List<byte[]> searchTokens ) {
        return new SearchRequest( searchTokens );
    }

    public static SearchRequest searchToken( List<byte[]> searchTokens, int maxResults ) {
        return new SearchRequest( searchTokens, maxResults, 0 );
    }

    public static SearchRequest searchToken(
            List<byte[]> searchTokens,
            int maxResults,
            int offset ) {
        return new SearchRequest( searchTokens, maxResults, offset );
    }

    @JsonProperty( Names.QUERY_FIELD )
    public List<byte[]> getSearchToken() {
        return searchToken;
    }

    @JsonProperty( Names.MAX_FIELD )
    public int getMaxResults() {
        return maxResults;
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
        } else if ( searchToken.size() != other.searchToken.size() ) {
            return false;
        } else {
            for ( int i = 0; i < searchToken.size(); ++i ) {
                if ( !Arrays.equals( searchToken.get( i ), other.searchToken.get( i ) ) ) {
                    return false;
                }
            }
        }

        return offset == other.offset && maxResults == other.maxResults;
    }

}
