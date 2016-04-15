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
    private final Set<UUID>                     objectIds;
    private final Map<UUID, Set<UUID>>          objectIdsToFilter;
    private final Map<UUID, Set<LoadLevel>>     typeLoadLevels;
    private final Map<UUID, VersionedObjectKey> createdAfter;
    private final int                           loadDepth;
    private final Optional<Integer>             pageSize;
    private final Optional<Integer>             offset;

    public ObjectTreeLoadRequest( Set<UUID> objectIds, Map<UUID, Set<LoadLevel>> typeLoadLevels ) {
        this(
                objectIds,
                Optional.<Map<UUID, Set<UUID>>> absent(),
                typeLoadLevels,
                Optional.<Map<UUID, VersionedObjectKey>> absent(),
                Optional.of( DEFAULT_DEPTH ),
                Optional.<Integer> absent(),
                Optional.<Integer> absent() );
    }

    @JsonCreator
    public ObjectTreeLoadRequest(
            @JsonProperty( Names.OBJECT_IDS_FIELD ) Set<UUID> objectIds,
            @JsonProperty( Names.OBJECTS_FILTER_FIELD ) Optional<Map<UUID, Set<UUID>>> objectIdsToFilter,
            @JsonProperty( Names.LOAD_LEVELS_FIELD ) Map<UUID, Set<LoadLevel>> typeLoadLevels,
            @JsonProperty( Names.CREATED_AFTER_FIELD ) Optional<Map<UUID, VersionedObjectKey>> createdAfter,
            @JsonProperty( Names.DEPTH_FIELD ) Optional<Integer> loadDepth,
            @JsonProperty( Names.PAGE_SIZE ) Optional<Integer> pageSize,
            @JsonProperty( Names.OFFSET ) Optional<Integer> offset) {
        this.objectIds = objectIds;
        this.objectIdsToFilter = objectIdsToFilter.or( ImmutableMap.<UUID, Set<UUID>> of() );
        this.typeLoadLevels = typeLoadLevels;
        this.createdAfter = createdAfter.or( ImmutableMap.<UUID, VersionedObjectKey> of() );
        this.loadDepth = loadDepth.or( DEFAULT_DEPTH );
        this.pageSize = pageSize;
        this.offset = offset;
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

    public Optional<Integer> getOffset() {
        return offset;
    }
}
