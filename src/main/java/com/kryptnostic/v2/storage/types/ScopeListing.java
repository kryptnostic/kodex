package com.kryptnostic.v2.storage.types;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;

public class ScopeListing {
    private final Optional<Integer>  page;
    private final Optional<Integer>  totalPages;
    private final Map<String, UUID>  scopeIds;
    private final Map<String, Scope> scopes;

    public ScopeListing(
            @JsonProperty( Names.PAGE_FIELD ) Optional<Integer> page,
            @JsonProperty( Names.TOTAL_FIELD ) Optional<Integer> totalPages,
            @JsonProperty( Names.SCOPE_IDS ) Map<String, UUID> scopeIds,
            @JsonProperty( Names.SCOPES ) Map<String, Scope> scopes) {
        this.page = page;
        this.totalPages = totalPages;
        this.scopeIds = scopeIds;
        this.scopes = scopes;
    }

    @JsonProperty( Names.PAGE_FIELD )
    public Optional<Integer> getPage() {
        return page;
    }

    @JsonProperty( Names.TOTAL_FIELD )
    public Optional<Integer> getTotalPages() {
        return totalPages;
    }

    @JsonProperty( Names.SCOPE_IDS )
    public Map<String, UUID> getScopeIds() {
        return scopeIds;
    }

    @JsonProperty( Names.SCOPES )
    public Map<String, Scope> getScopes() {
        return scopes;
    }
}
