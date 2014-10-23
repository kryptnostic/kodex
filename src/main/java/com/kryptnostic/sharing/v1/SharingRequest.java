package com.kryptnostic.sharing.v1;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.users.v1.UserKey;

public final class SharingRequest {
    private final byte[] encryptedDocumentId;
    private final Set<UserKey> userKeys;
    private final byte[] encryptedSharingKey;

    @JsonCreator
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) byte[] encryptedDocumentId,
            @JsonProperty( Names.USERS_FIELD ) Set<UserKey> userKey,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey ) {
        this.encryptedDocumentId = encryptedDocumentId;
        this.userKeys = userKey;
        this.encryptedSharingKey = encryptedSharingKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public byte[] getEncryptedDocumentId() {
        return encryptedDocumentId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Set<UserKey> getUserKeys() {
        return userKeys;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }
}
