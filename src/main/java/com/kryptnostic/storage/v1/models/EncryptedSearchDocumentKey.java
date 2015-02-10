package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.sharing.v1.models.DocumentId;

public class EncryptedSearchDocumentKey implements Serializable, Comparable<EncryptedSearchDocumentKey> {
    private static final long              serialVersionUID   = -1727973926753941167L;
    public static final String             FIELD_SEARCH_NONCE = "searchNonce";
    public static final String             FIELD_BRIDGE_KEY   = "bridgeKey";

    private final EncryptedSearchBridgeKey bridgeKey;
    private final DocumentId               documentId;

    @JsonCreator
    public EncryptedSearchDocumentKey(
            @JsonProperty( FIELD_BRIDGE_KEY ) EncryptedSearchBridgeKey bridgeKey,
            @JsonProperty( Names.ID_FIELD ) DocumentId documentId ) {
        this.bridgeKey = bridgeKey;
        this.documentId = documentId;
    }

    @JsonProperty( Names.ID_FIELD )
    public DocumentId getDocumentId() {
        return documentId;
    }

    @JsonProperty( FIELD_BRIDGE_KEY )
    public EncryptedSearchBridgeKey getBridgeKey() {
        return bridgeKey;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) {
            return false;
        }
        EncryptedSearchDocumentKey other = (EncryptedSearchDocumentKey) o;
        return bridgeKey.equals( other.bridgeKey ) && documentId.equals( documentId );
    }

    @Override
    public int compareTo( EncryptedSearchDocumentKey o ) {
        return documentId.getDocumentId().compareTo( o.getDocumentId().getDocumentId() );
    }
}
