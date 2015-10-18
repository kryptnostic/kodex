package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v2.types.TypeUUIDs;

public class PendingObjectRequest {
    private static final boolean INHERIT_OWNERSHIP_DEFAULT = true;
    private final UUID           type;
    private final Optional<UUID> objectId;
    private final Optional<UUID> parentObjectId;
    private final boolean        inheritOwnership;

    @JsonIgnore
    public PendingObjectRequest() {
        this( TypeUUIDs.DEFAULT, Optional.<UUID> absent(), Optional.<UUID> absent(), Optional.<Boolean> absent() );
    }

    @JsonCreator
    public PendingObjectRequest(
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<UUID> parentObjectId,
            @JsonProperty( Names.ID_FIELD ) Optional<UUID> objectId,
            @JsonProperty( Names.INHERIT_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership ) {
        this.type = type;
        this.parentObjectId = parentObjectId;
        this.objectId = objectId;
        ;
        this.inheritOwnership = inheritOwnership.or( INHERIT_OWNERSHIP_DEFAULT );
    }

    @JsonProperty( Names.TYPE_FIELD )
    public UUID getType() {
        return type;
    }

    @JsonProperty( Names.ID_FIELD )
    public Optional<UUID> getObjectId() {
        return objectId;
    }
    
    @JsonProperty( Names.PARENT_OBJECT_ID_FIELD )
    public Optional<UUID> getParentId() {
        return parentObjectId;
    }

    @JsonProperty( Names.INHERIT_OWNERSHIP_FIELD )
    public boolean isInheritOwnership() {
        return inheritOwnership;
    }

}
