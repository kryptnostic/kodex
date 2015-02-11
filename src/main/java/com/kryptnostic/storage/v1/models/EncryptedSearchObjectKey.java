package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class EncryptedSearchObjectKey implements Serializable, Comparable<EncryptedSearchObjectKey> {
    private static final long              serialVersionUID = -1727973926753941167L;

    private final EncryptedSearchBridgeKey bridgeKey;
    private final String                   objectId;

    @JsonCreator
    public EncryptedSearchObjectKey(
            @JsonProperty( Names.KEY_FIELD ) EncryptedSearchBridgeKey bridgeKey,
            @JsonProperty( Names.ID_FIELD ) String objectId ) {
        this.bridgeKey = bridgeKey;
        this.objectId = objectId;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
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
        EncryptedSearchObjectKey other = (EncryptedSearchObjectKey) o;
        return bridgeKey.equals( other.bridgeKey ) && objectId.equals( objectId );
    }

    @Override
    public int compareTo( EncryptedSearchObjectKey o ) {
        return objectId.compareTo( o.getObjectId() );
    }
}
