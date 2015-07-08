package com.kryptnostic.storage.v1.models.request;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.ObjectMetadata;

public class PendingObjectRequest {
    private final String           type;
    private final @Nullable String parentObjectId;
    private final int              size;

    @JsonIgnore
    public PendingObjectRequest() {
        this( ObjectMetadata.DEFAULT_TYPE, null, -1 );
    }

    @JsonCreator
    public PendingObjectRequest(
            @JsonProperty( Names.TYPE_FIELD ) String type,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) String parentObjectId,
            @JsonProperty( Names.SIZE_FIELD ) int size ) {
        this.type = type;
        this.parentObjectId = parentObjectId;
        this.size = size;
    }

    @JsonProperty( Names.TYPE_FIELD )
    public String getType() {
        return type;
    }

    @JsonProperty( Names.PARENT_OBJECT_ID_FIELD )
    public String getParentId() {
        return parentObjectId;
    }

    @JsonProperty( Names.SIZE_FIELD )
    public int getSize() {
        return size;
    }

    @JsonIgnore
    public boolean isChildObjectRequest() {
        return parentObjectId != null;
    }

}
