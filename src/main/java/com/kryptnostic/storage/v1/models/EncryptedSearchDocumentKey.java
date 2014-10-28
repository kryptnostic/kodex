package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;

public class EncryptedSearchDocumentKey implements Serializable {
    private static final long serialVersionUID = -1727973926753941167L;
    public static final String FIELD_SEARCH_NONCE = "searchNonce";
    public static final String FIELD_BRIDGE_KEY = "bridgeKey";

    private final BitVector searchNonce;
    private final EncryptedSearchBridgeKey bridgeKey;

    @JsonCreator
    public EncryptedSearchDocumentKey(@JsonProperty(FIELD_SEARCH_NONCE) BitVector searchNonce,
            @JsonProperty(FIELD_BRIDGE_KEY) EncryptedSearchBridgeKey bridgeKey) {
        this.searchNonce = searchNonce;
        this.bridgeKey = bridgeKey;
    }

    @JsonProperty(FIELD_SEARCH_NONCE)
    public BitVector getSearchNonce() {
        return searchNonce;
    }

    @JsonProperty(FIELD_BRIDGE_KEY)
    public EncryptedSearchBridgeKey getBridgeKey() {
        return bridgeKey;
    }

    @Override
    public boolean equals(Object o) {
        EncryptedSearchDocumentKey other = (EncryptedSearchDocumentKey) o;
        return searchNonce.equals(other.searchNonce) && bridgeKey.equals(other.bridgeKey);
    }
}
