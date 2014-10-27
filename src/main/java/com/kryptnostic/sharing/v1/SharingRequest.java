package com.kryptnostic.sharing.v1;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.users.v1.UserKey;

public final class SharingRequest implements Serializable {
    private static final long serialVersionUID = 8493560981719181963L;
    private final Map<UserKey, byte[]> userKeys;
    private final byte[]               encryptedDocumentId;
    private final byte[]               encryptedSharingKey;
    private final byte[]               encryptedDocumentKey;

    @JsonCreator
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) byte[] encryptedDocumentId,
            @JsonProperty( Names.USERS_FIELD ) Map<UserKey, byte[]> userKey,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey,
            @JsonProperty( Names.DOCUMENT_KEY_FIELD ) byte[] encryptedDocumentKey ) {
        this.encryptedDocumentId = encryptedDocumentId;
        this.userKeys = userKey;
        this.encryptedSharingKey = encryptedSharingKey;
        this.encryptedDocumentKey = encryptedDocumentKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public byte[] getEncryptedDocumentId() {
        return encryptedDocumentId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Map<UserKey, byte[]> getUserKeys() {
        return userKeys;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }

    @JsonProperty( Names.DOCUMENT_KEY_FIELD )
    public byte[] getEncryptedDocumentKey() {
        return encryptedDocumentKey;
    }
}
