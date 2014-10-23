package com.kryptnostic.sharing.v1.requests;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.PairedEncryptedSearchDocumentKey;

public final class KeyRegistrationRequest {
    private final Map<UUID, PairedEncryptedSearchDocumentKey> searchDocumentKeys;

    public KeyRegistrationRequest(
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" ) final Map<UUID, PairedEncryptedSearchDocumentKey> searchDocumentKeys ) {
        this.searchDocumentKeys = searchDocumentKeys;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" )
    public Map<UUID, PairedEncryptedSearchDocumentKey> getSearchDocumentKeys() {
        return searchDocumentKeys;
    }
}
