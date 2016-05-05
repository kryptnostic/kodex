package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.kryptnostic.v2.constants.Names;

@Immutable
public class ObjectTreeLoadRequest {
    private static final int                    DEFAULT_DEPTH = 0;
    private static final int                    DEFAULT_DIRECTION = -1;
    @Deprecated // no longer used once the new getObjectByTypeAndLoadLevelPaged calls are in use
    private final Set<UUID>                     objectIds;
    @Deprecated
    private final Map<UUID, VersionedObjectKey> createdAfter;
    @Deprecated
    private final Optional<Integer>             pageSize;

    private final Map<UUID, Set<UUID>>          objectIdsToFilter;

    private final Map<UUID, Set<LoadLevel>>     typeLoadLevels;
    private final int                           loadDepth;

    public ObjectTreeLoadRequest( Set<UUID> objectIds, Map<UUID, Set<LoadLevel>> typeLoadLevels ) {
        this(
                objectIds,
                Optional.<Map<UUID, Set<UUID>>> absent(),
                typeLoadLevels,
                Optional.<Map<UUID, VersionedObjectKey>> absent(),
                Optional.of( DEFAULT_DEPTH ),
                Optional.<Integer> absent() );
    }

    @JsonCreator
    public ObjectTreeLoadRequest(
            @JsonProperty( Names.OBJECT_IDS_FIELD ) Set<UUID> objectIds,
            @JsonProperty( Names.OBJECTS_FILTER_FIELD ) Optional<Map<UUID, Set<UUID>>> objectIdsToFilter,
            @JsonProperty( Names.LOAD_LEVELS_FIELD ) Map<UUID, Set<LoadLevel>> typeLoadLevels,
            @JsonProperty( Names.CREATED_AFTER_FIELD ) Optional<Map<UUID, VersionedObjectKey>> createdAfter,
            @JsonProperty( Names.DEPTH_FIELD ) Optional<Integer> loadDepth,
            @JsonProperty( Names.PAGE_SIZE ) Optional<Integer> pageSize ) {
        this.objectIds = objectIds;
        this.pageSize = pageSize;
        this.typeLoadLevels = typeLoadLevels;
        this.createdAfter = createdAfter.or( ImmutableMap.<UUID, VersionedObjectKey> of() );
        this.loadDepth = loadDepth.or( DEFAULT_DEPTH );
        this.objectIdsToFilter = objectIdsToFilter.or( ImmutableMap.<UUID, Set<UUID>> of() );
    }

    @JsonProperty( Names.OBJECT_IDS_FIELD )
    public Set<UUID> getObjectIds() {
        return objectIds;
    }

    @JsonProperty( Names.OBJECTS_FILTER_FIELD )
    public Map<UUID, Set<UUID>> getObjectIdsToFilter() {
        return objectIdsToFilter;
    }

    @JsonProperty( Names.LOAD_LEVELS_FIELD )
    public Map<UUID, Set<LoadLevel>> getTypeLoadLevels() {
        return typeLoadLevels;
    }

    @JsonProperty( Names.CREATED_AFTER_FIELD )
    public Map<UUID, VersionedObjectKey> getCreatedAfter() {
        return createdAfter;
    }

    @JsonProperty( Names.DEPTH_FIELD )
    public int getLoadDepth() {
        return loadDepth;
    }

    public Optional<Integer> getPageSize() {
        return pageSize;
    }

}
