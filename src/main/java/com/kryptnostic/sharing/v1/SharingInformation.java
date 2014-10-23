package com.kryptnostic.sharing.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class SharingInformation {
    private final byte[]     encryptedSearchDocumentKey;
    private final DocumentId documentKey;

    @JsonCreator
    public SharingInformation( @JsonProperty(Names.ID_FIELD) DocumentId documentKey, @JsonProperty(Names.DOCUMENT_SHARING_KEY_FIELD) byte[] encryptedSearchDocumentKey ) {
        this.encryptedSearchDocumentKey = encryptedSearchDocumentKey;
        this.documentKey = documentKey;
    }
    
    @JsonProperty(Names.DOCUMENT_SHARING_KEY_FIELD)
    public byte[] getEncryptedSearchDocumentKey() {
        return encryptedSearchDocumentKey;
    }

    @JsonProperty(Names.ID_FIELD) 
    public DocumentId getDocumentId() {
        return documentKey;
    }
}
