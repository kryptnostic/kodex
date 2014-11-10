package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public class PairedEncryptedSearchDocumentKey implements Serializable {
    private static final long                serialVersionUID = -6584084313085024163L;
    private final EncryptedSearchDocumentKey searchDocumentKey;
    private final DocumentId                 documentId;

    public PairedEncryptedSearchDocumentKey(
            @JsonProperty( Names.ID_FIELD ) DocumentId documentId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) final EncryptedSearchDocumentKey searchDocumentKey ) {
        this.documentId = documentId;
        this.searchDocumentKey = searchDocumentKey;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public EncryptedSearchDocumentKey getSearchDocumentKey() {
        return searchDocumentKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public DocumentId getDocumentId() {
        return documentId;
    }
}
