package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.SharingRequest;

public class Share implements Serializable {
    private final byte[] encryptedDocumentId;
    private final byte[] encryptedSharingKey;

    public Share(
            @JsonProperty( Names.ID_FIELD ) byte[] encryptedDocumentId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey ) {
        this.encryptedDocumentId = encryptedDocumentId;
        this.encryptedSharingKey = encryptedSharingKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public byte[] getEncryptedDocumentId() {
        return encryptedDocumentId;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }

    public static Share fromSharingRequest( SharingRequest request ) {
        return new Share( request.getEncryptedDocumentId(), request.getEncryptedSharingKey() );
    }
}
