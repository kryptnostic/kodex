package com.kryptnostic.v2.search;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;

/**
 * Model for a simple term query.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class TermQuery {
    private final Iterable<byte[]> fheEncryptedSearchTerms;
    private final Optional<UUID>   objectIdMarker;
    private final Optional<Double> scoreMarker;

    public TermQuery( Iterable<byte[]> fheEncryptedSearchTerms ) {
        this( fheEncryptedSearchTerms, Optional.<UUID> absent(), Optional.<Double> absent() );
    }

    @JsonCreator
    public TermQuery(
            @JsonProperty( Names.TERMS_FIELD ) Iterable<byte[]> fheEncryptedSearchTerms,
            @JsonProperty( Names.OBJECT_ID_FIELD ) Optional<UUID> objectIdMarker,
            @JsonProperty( Names.SCORE_FIELD ) Optional<Double> scoreMarker) {
        this.fheEncryptedSearchTerms = fheEncryptedSearchTerms;
        this.objectIdMarker = objectIdMarker;
        this.scoreMarker = scoreMarker;
    }

    @JsonProperty( Names.TERMS_FIELD )
    public Iterable<byte[]> getFheEncryptedSearchTerms() {
        return fheEncryptedSearchTerms;
    }

    @JsonProperty( Names.OBJECT_ID_FIELD )
    public Optional<UUID> getObjectIdMarker() {
        return objectIdMarker;
    }

    @JsonProperty( Names.SCORE_FIELD )
    public Optional<Double> getScoreMarker() {
        return scoreMarker;
    }

}
