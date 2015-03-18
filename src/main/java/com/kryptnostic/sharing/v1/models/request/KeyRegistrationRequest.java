package com.kryptnostic.sharing.v1.models.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v1.models.EncryptedSearchObjectKey;

public final class KeyRegistrationRequest {
    private final Set<EncryptedSearchObjectKey> searchKeys;

    public KeyRegistrationRequest(
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" ) final Set<EncryptedSearchObjectKey> searchKeys ) {
        this.searchKeys = searchKeys;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD + "s" )
    public Set<EncryptedSearchObjectKey> getSearchKeys() {
        return searchKeys;
    }
}
