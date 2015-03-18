package com.kryptnostic.storage.v1.models.request;

import com.kryptnostic.storage.v1.models.StorageRequestBuilder;

public class StorageRequest {
    private final String  objectId;
    private final String  objectBody;
    private final boolean isSearchable;
    private final boolean isStoreable;
    private final String  type;

    public StorageRequest( String objectId, String objectBody, boolean isSearchable, boolean isStoreable, String type ) {
        super();
        this.objectId = objectId;
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
