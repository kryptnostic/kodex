package com.kryptnostic.search.v1.models.response;

import java.util.Collection;

import com.kryptnostic.kodex.v1.models.response.BasicResponse;
import com.kryptnostic.search.v1.models.SearchResult;

public class SearchResultResponse extends BasicResponse<Collection<SearchResult>> {
    public SearchResultResponse(Collection<SearchResult> result, int status, boolean success) {
        super(result, status, success);
    }
}
