package com.kryptnostic.v2.search;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.storage.models.VersionedObjectKeySet;

/**
 * Represents a set of search results for a pa
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class SearchResult {
    private static final String         METADATA = "metadata";
    private static final String         SCORE    = "score";
    private static final String         DATE     = "date";

    private final VersionedObjectKeySet metadata;
    private final Integer               score;
    private final DateTime              date;

    @JsonCreator
    public SearchResult(
            @JsonProperty( METADATA ) VersionedObjectKeySet metadata,
            @JsonProperty( SCORE ) Integer score,
            @JsonProperty( DATE ) DateTime date ) {
        this.metadata = metadata;
        this.score = score;
        this.date = date;
    }

    @JsonProperty( METADATA )
    public VersionedObjectKeySet getMetadata() {
        return metadata;
    }

    @JsonProperty( SCORE )
    public Integer getScore() {
        return score;
    }

    @JsonProperty( DATE )
    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "SearchResult[ metadata=" + metadata + ", score=" + score + ", date=" + date + "]";
    }
}
