package com.kryptnostic.sharing.v1.models.request;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.DocumentId;

public final class SharingRequest implements Serializable {
    private static final long          serialVersionUID = 8493560981719181963L;
    private final Map<UserKey, byte[]> userKeys;
    private final DocumentId           documentId;
    private final byte[]               encryptedSharingKey;
    private final byte[]               encryptedDocumentKey;

    @JsonCreator
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) DocumentId encryptedDocumentId,
            @JsonProperty( Names.USERS_FIELD ) Map<UserKey, byte[]> userKey,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey,
            @JsonProperty( Names.DOCUMENT_KEY_FIELD ) byte[] encryptedDocumentKey ) {
        this.documentId = encryptedDocumentId;
        this.userKeys = userKey;
        this.encryptedSharingKey = encryptedSharingKey;
        this.encryptedDocumentKey = encryptedDocumentKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public DocumentId getDocumentId() {
        return documentId;
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
