package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class EncryptedSearchDocumentKey implements Serializable, Comparable<EncryptedSearchDocumentKey> {
    private static final long              serialVersionUID = -1727973926753941167L;

    private final EncryptedSearchBridgeKey bridgeKey;
    private final String                   documentId;

    @JsonCreator
    public EncryptedSearchDocumentKey(
            @JsonProperty( Names.KEY_FIELD ) EncryptedSearchBridgeKey bridgeKey,
            @JsonProperty( Names.ID_FIELD ) String documentId ) {
        this.bridgeKey = bridgeKey;
        this.documentId = documentId;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getDocumentId() {
        return documentId;
    }

    @JsonProperty( Names.KEY_FIELD )
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
        return documentId.compareTo( o.getDocumentId() );
    }
}
