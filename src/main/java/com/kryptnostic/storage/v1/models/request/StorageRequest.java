package com.kryptnostic.storage.v1.models.request;

public class StorageRequest {
    private final String  objectId;
    private final String  objectBody;
    private final boolean isSearchable;
    private final boolean isStoreable;

    public StorageRequest( String objectId, String objectBody, boolean isSearchable, boolean isStoreable ) {
        super();
        this.objectId = objectId;
        this.objectBody = objectBody;
        this.isSearchable = isSearchable;
        this.isStoreable = isStoreable;
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
