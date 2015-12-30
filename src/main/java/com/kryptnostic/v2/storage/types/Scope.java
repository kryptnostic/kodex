package com.kryptnostic.v2.storage.types;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public final class Scope {
    private final UUID              scopeId;
    private final Map<String, UUID> types;

    @JsonCreator
    public Scope(
            @JsonProperty( Names.SCOPE_ID ) UUID scopeId,
            @JsonProperty( Names.TYPES ) Map<String, UUID> types) {
        this.scopeId = scopeId;
        this.types = types;
    }

    @JsonProperty( Names.SCOPE_ID )
    public UUID getScopeId() {
        return scopeId;
    }

    @JsonProperty( Names.TYPES )
    public Map<String, UUID> getTypes() {
        return types;
    }
}
