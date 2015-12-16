package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;

@Immutable
public class ObjectTreeLoadRequest {
    private static final int           DEFAULT_DEPTH = 0;
    private final Set<UUID>            objectIds;
    private final Map<UUID, LoadLevel> typeLoadLevels;
    private final int                  loadDepth;

    public ObjectTreeLoadRequest( Set<UUID> objectIds, Map<UUID, LoadLevel> typeLoadLevels ) {
        this( objectIds, typeLoadLevels, Optional.of( DEFAULT_DEPTH ) );
    }

    @JsonCreator
    public ObjectTreeLoadRequest(
            @JsonProperty( Names.OBJECT_IDS ) Set<UUID> objectIds,
            @JsonProperty( Names.LOAD_LEVELS ) Map<UUID, LoadLevel> typeLoadLevels,
            @JsonProperty( Names.DEPTH ) Optional<Integer> loadDepth ) {
        this.objectIds = objectIds;
        this.typeLoadLevels = typeLoadLevels;
        this.loadDepth = loadDepth.or( DEFAULT_DEPTH );
    }

    @JsonProperty( Names.OBJECT_IDS )
    public Set<UUID> getObjectIds() {
        return objectIds;
    }

    @JsonProperty( Names.LOAD_LEVELS )
    public Map<UUID, LoadLevel> getTypeLoadLevels() {
        return typeLoadLevels;
    }

    @JsonProperty( Names.DEPTH )
    public int getLoadDepth() {
        return loadDepth;
    }
}
