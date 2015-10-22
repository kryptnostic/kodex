package com.kryptnostic.storage.v2.models;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

@Immutable
public class ObjectTreeLoadRequest {
    private final Set<UUID>            objectIds;
    private final Map<UUID, LoadLevel> typeLoadLevels;

    @JsonCreator
    public ObjectTreeLoadRequest(
            @JsonProperty( Names.OBJECT_IDS ) Set<UUID> objectIds,
            @JsonProperty( Names.LOAD_LEVELS ) Map<UUID, LoadLevel> typeLoadLevels ) {
        this.objectIds = objectIds;
        this.typeLoadLevels = typeLoadLevels;
    }

    @JsonProperty( Names.OBJECT_IDS )
    public Set<UUID> getObjectIds() {
        return objectIds;
    }

    @JsonProperty( Names.LOAD_LEVELS )
    public Map<UUID, LoadLevel> getTypeLoadLevels() {
        return typeLoadLevels;
    }

}
