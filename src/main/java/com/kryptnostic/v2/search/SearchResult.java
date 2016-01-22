package com.kryptnostic.v2.search;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

/**
 * Represents a set of search results for a pa
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class SearchResult implements Comparable<SearchResult> {

    private final String searchToken;
    private final UUID   metadataObjectId;
    private final double score;

    @JsonCreator
    public SearchResult(
            @JsonProperty( Names.TOKEN_FIELD ) String searchToken,
            @JsonProperty( Names.METADATA_FIELD ) UUID metadataObjectId,
            @JsonProperty( Names.SCORE_FIELD ) double score) {
        this.searchToken = searchToken;
        this.metadataObjectId = metadataObjectId;
        this.score = score;
    }

    @JsonProperty( Names.TOKEN_FIELD )
    public String getSearchToken() {
        return searchToken;

    }

    @JsonProperty( Names.METADATA_FIELD )
    public UUID getMetadata() {
        return metadataObjectId;
    }

    @JsonProperty( Names.SCORE_FIELD )
    public double getScore() {
        return score;
    }

    @Override
    public int compareTo( SearchResult o ) {
        return Double.compare( score, o.getScore() );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( metadataObjectId == null ) ? 0 : metadataObjectId.hashCode() );
        long temp;
        temp = Double.doubleToLongBits( score );
        result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
        result = prime * result + ( ( searchToken == null ) ? 0 : searchToken.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof SearchResult ) ) {
            return false;
        }
        SearchResult other = (SearchResult) obj;
        if ( metadataObjectId == null ) {
            if ( other.metadataObjectId != null ) {
                return false;
            }
        } else if ( !metadataObjectId.equals( other.metadataObjectId ) ) {
            return false;
        }
        if ( Double.doubleToLongBits( score ) != Double.doubleToLongBits( other.score ) ) {
            return false;
        }
        if ( searchToken == null ) {
            if ( other.searchToken != null ) {
                return false;
            }
        } else if ( !searchToken.equals( other.searchToken ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SearchResult [searchToken=" + searchToken + ", metadataObjectId=" + metadataObjectId + ", score="
                + score + "]";
    }

}
