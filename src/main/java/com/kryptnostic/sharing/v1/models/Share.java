package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.SharingRequest;
import com.kryptnostic.users.v1.UserKey;

public class Share implements Serializable {
    private static final long serialVersionUID = 1145823070022684715L;
    private final byte[]      encryptedDocumentId;
    private final byte[]      encryptedSharingKey;
    private final byte[]      encryptedDocumentKey;
    private final byte[]      seal;

    public Share(
            @JsonProperty( Names.ID_FIELD ) byte[] encryptedDocumentId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey,
            @JsonProperty( Names.DOCUMENT_KEY_FIELD ) byte[] encryptedDocumentKey,
            @JsonProperty( Names.PASSWORD_FIELD ) byte[] seal ) {
        this.encryptedDocumentId = encryptedDocumentId;
        this.encryptedSharingKey = encryptedSharingKey;
        this.encryptedDocumentKey = encryptedDocumentKey;
        this.seal = seal;
    }

    @JsonProperty( Names.ID_FIELD )
    public byte[] getEncryptedDocumentId() {
        return encryptedDocumentId;
    }

    @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }

    @JsonProperty( Names.DOCUMENT_KEY_FIELD )
    public byte[] getEncryptedDocumentKey() {
        return encryptedDocumentKey;
    }
    
    @JsonProperty( Names.PASSWORD_FIELD )
    public byte[] getSeal() {
        return seal;
    }

    public static Share fromSharingRequest( UserKey user, SharingRequest request ) {
        return new Share(
                request.getEncryptedDocumentId(),
                request.getEncryptedSharingKey(),
                request.getEncryptedDocumentKey(),
                request.getUserKeys().get( user ) );
    }
}
