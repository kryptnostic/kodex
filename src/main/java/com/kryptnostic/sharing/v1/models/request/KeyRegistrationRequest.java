package com.kryptnostic.sharing.v1.models.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

public final class KeyRegistrationRequest {
    private final Set<EncryptedSearchDocumentKey> searchDocumentKeys;

    public KeyRegistrationRequest(
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" ) final Set<EncryptedSearchDocumentKey> searchDocumentKeys ) {
        this.searchDocumentKeys = searchDocumentKeys;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" )
    public Set<EncryptedSearchDocumentKey> getSearchDocumentKeys() {
        return searchDocumentKeys;
    }
}
