package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.request.SharingRequest;

/**
 * Represents the information involved in securely sharing a document from on user
 * to another. 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt; 
 *
 */
public class Share implements Serializable {
    private static final long serialVersionUID = 1145823070022684715L;
    private final DocumentId      documentId;
    private final byte[]      encryptedSharingKey;
    private final byte[]      encryptedDocumentKey;
    private final byte[]      seal;

    public Share(
            @JsonProperty( Names.ID_FIELD ) DocumentId documentId,
            @JsonProperty( Names.DOCUMENT_SHARING_KEY_FIELD ) byte[] encryptedSharingKey,
            @JsonProperty( Names.DOCUMENT_KEY_FIELD ) byte[] encryptedDocumentKey,
            @JsonProperty( Names.PASSWORD_FIELD ) byte[] seal ) {
        this.documentId = documentId;
        this.encryptedSharingKey = encryptedSharingKey;
        this.encryptedDocumentKey = encryptedDocumentKey;
        this.seal = seal;
    }

    @JsonProperty( Names.ID_FIELD )
    public DocumentId getDocumentId() {
        return documentId;
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
                request.getDocumentId(),
                request.getEncryptedSharingKey(),
                request.getEncryptedDocumentKey(),
                request.getUserKeys().get( user ) );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( documentId == null ) ? 0 : documentId.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof Share ) ) {
            return false;
        }
        Share other = (Share) obj;
        if ( documentId == null ) {
            if ( other.documentId != null ) {
                return false;
            }
        } else if ( !documentId.equals( other.documentId ) ) {
            return false;
        }
        return true;
    }
}
