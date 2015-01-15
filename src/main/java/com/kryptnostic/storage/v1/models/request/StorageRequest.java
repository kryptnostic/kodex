package com.kryptnostic.storage.v1.models.request;

public class StorageRequest {
    private final String  documentId;
    private final String  documentBody;
    private final boolean isSearchable;
    private final boolean isStoreable;

    public StorageRequest( String documentId, String documentBody, boolean isSearchable, boolean isStoreable ) {
        super();
        this.documentId = documentId;
        this.documentBody = documentBody;
        this.isSearchable = isSearchable;
        this.isStoreable = isStoreable;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getDocumentBody() {
        return documentBody;
    }

    public boolean isSearchable() {
        return isSearchable;
    }

    public boolean isStoreable() {
        return isStoreable;
    }

}
