package com.kryptnostic.storage.v1.models.request;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.ObjectMetadata;

public class PendingObjectRequest {
    private static final boolean   INHERIT_OWNERSHIP_DEFAULT = true;
    private final String           type;
    private final @Nullable String parentObjectId;
    private final boolean          inheritOwnership;

    @JsonIgnore
    public PendingObjectRequest() {
        this( ObjectMetadata.DEFAULT_TYPE, null, Optional.<Boolean>absent() );
    }

    @JsonCreator
    public PendingObjectRequest(
            @JsonProperty( Names.TYPE_FIELD ) String type,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) String parentObjectId,
            @JsonProperty( Names.INHERIT_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership ) {
        this.type = type;
        this.parentObjectId = parentObjectId;
        this.inheritOwnership = inheritOwnership.or( INHERIT_OWNERSHIP_DEFAULT );
    }

    @JsonProperty( Names.TYPE_FIELD )
    public String getType() {
        return type;
    }

    @JsonProperty( Names.PARENT_OBJECT_ID_FIELD )
    public String getParentId() {
        return parentObjectId;
    }

    @JsonIgnore
    public boolean isChildObjectRequest() {
        return parentObjectId != null;
    }
    
    @JsonProperty( Names.INHERIT_OWNERSHIP_FIELD ) 
    public boolean isInheritOwnership() {
        return inheritOwnership;
    }

}
