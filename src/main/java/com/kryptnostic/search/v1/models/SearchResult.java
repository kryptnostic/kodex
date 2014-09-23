package com.kryptnostic.search.v1.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.models.Encryptable;

public class SearchResult {
    private static final String METADATA = "metadata";
    private static final String SCORE = "score";
    private static final String DATE = "date";
    
    private final Collection<Encryptable<?>> metadata;
    private final Integer score;
    private final String date;

    @JsonCreator
    public SearchResult(
            @JsonProperty( METADATA ) Collection<Encryptable<?>> metadata, 
            @JsonProperty( SCORE ) Integer score, 
            @JsonProperty( DATE ) String date) {
        this.metadata = metadata;
        this.score = score;
        this.date = date;
    }

    @JsonProperty( METADATA ) 
    public Collection<Encryptable<?>> getMetadata() {
        return metadata;
    }

    @JsonProperty( SCORE ) 
    public Integer getScore() {
        return score;
    }

    @JsonProperty( DATE ) 
    public String getDate() {
        return date;
    }
}
