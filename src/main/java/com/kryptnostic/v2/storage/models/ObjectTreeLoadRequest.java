package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.kryptnostic.v2.constants.Names;

@Immutable
public class ObjectTreeLoadRequest {
    private static final int                    DEFAULT_DEPTH = 0;
    private final Set<UUID>                     objectIds;
    private final Set<VersionedObjectKey>       objectIdsToFilter;
    private final Map<UUID, Set<LoadLevel>>     typeLoadLevels;
    private final Map<UUID, VersionedObjectKey> createdAfter;
    private final int                           loadDepth;

    public ObjectTreeLoadRequest( Set<UUID> objectIds, Map<UUID, Set<LoadLevel>> typeLoadLevels ) {
        this(
                objectIds,
                Optional.<Set<VersionedObjectKey>> absent(),
                typeLoadLevels,
                Optional.<Map<UUID, VersionedObjectKey>> absent(),
                Optional.of( DEFAULT_DEPTH ) );
    }

    @JsonCreator
    public ObjectTreeLoadRequest(
            @JsonProperty( Names.OBJECT_IDS ) Set<UUID> objectIds,
            @JsonProperty( Names.OBJECTS_FILTER ) Optional<Set<VersionedObjectKey>> objectIdsToFilter,
            @JsonProperty( Names.LOAD_LEVELS ) Map<UUID, Set<LoadLevel>> typeLoadLevels,
            @JsonProperty( Names.CREATED_AFTER ) Optional<Map<UUID, VersionedObjectKey>> createdAfter,
            @JsonProperty( Names.DEPTH ) Optional<Integer> loadDepth) {
        this.objectIds = objectIds;
        this.objectIdsToFilter = objectIdsToFilter.or( ImmutableSet.<VersionedObjectKey> of() );
        this.typeLoadLevels = typeLoadLevels;
        this.createdAfter = createdAfter.or( ImmutableMap.<UUID, VersionedObjectKey> of() );
        this.loadDepth = loadDepth.or( DEFAULT_DEPTH );
    }

    @JsonProperty( Names.OBJECT_IDS )
    public Set<UUID> getObjectIds() {
        return objectIds;
    }

    @JsonProperty( Names.OBJECTS_FILTER )
    public Set<VersionedObjectKey> getObjectIdsToFilter() {
        return objectIdsToFilter;
    }

    @JsonProperty( Names.LOAD_LEVELS )
    public Map<UUID, Set<LoadLevel>> getTypeLoadLevels() {
        return typeLoadLevels;
    }

    @JsonProperty( Names.CREATED_AFTER )
    public Map<UUID, VersionedObjectKey> getCreatedAfter() {
        return createdAfter;
    }

    @JsonProperty( Names.DEPTH )
    public int getLoadDepth() {
        return loadDepth;
    }
}
