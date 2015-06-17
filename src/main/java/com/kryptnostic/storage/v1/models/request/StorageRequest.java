package com.kryptnostic.storage.v1.models.request;

import com.google.common.base.Optional;
import com.kryptnostic.storage.v1.models.StorageRequestBuilder;

public class StorageRequest {
    private final String  objectId;
    private final Optional<String>  parentObjectId;
    private final String  objectBody;
    private final boolean isSearchable;
    private final boolean isStoreable;
    private final String  type;

    public StorageRequest( String objectId, Optional<String> parentObjectId, String objectBody, boolean isSearchable, boolean isStoreable, String type ) {
        super();
        this.objectId = objectId;
        this.parentObjectId = parentObjectId;
        this.objectBody = objectBody;
        this.isSearchable = isSearchable;
        this.isStoreable = isStoreable;
        this.type = type;
    }

    public static StorageRequestBuilder builder() {
        return new StorageRequestBuilder();
    }

    public String getType() {
        return type;
    }

    public Optional<String> getParentObjectId() {
        return parentObjectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getObjectBody() {
        return objectBody;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public boolean isStoreable() {
        return isStoreable;
    }

}
