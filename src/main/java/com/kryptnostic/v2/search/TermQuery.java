package com.kryptnostic.v2.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TermQuery {
    private final List<byte[]> fheEncryptedSearchTerms;
    private final int          maxResults;

    @JsonCreator
    public TermQuery(
            List<byte[]> fheEncryptedSearchTerms,
            int maxResults,
            int offset ) {
        this.fheEncryptedSearchTerms = fheEncryptedSearchTerms;
        this.maxResults = maxResults;
    }

    public List<byte[]> getFheEncryptedSearchTerms() {
        return fheEncryptedSearchTerms;
    }

    public int getMaxResults() {
        return maxResults;
    }
}
