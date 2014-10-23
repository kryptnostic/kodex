package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public class PairedEncryptedSearchDocumentKey implements Serializable {
    private final EncryptedSearchDocumentKey searchDocumentKey;
    private final byte[]                     encryptedDocumentId;

    public PairedEncryptedSearchDocumentKey(
            @JsonProperty( Names.ID_FIELD ) byte[] encryptedDocumentId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) final EncryptedSearchDocumentKey searchDocumentKey ) {
        this.encryptedDocumentId = encryptedDocumentId;
        this.searchDocumentKey = searchDocumentKey;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public EncryptedSearchDocumentKey getSearchDocumentKey() {
        return searchDocumentKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public byte[] getEncryptedDocumentId() {
        return encryptedDocumentId;
    }
}
