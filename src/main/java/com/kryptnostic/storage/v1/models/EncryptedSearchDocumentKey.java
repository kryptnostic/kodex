package com.kryptnostic.storage.v1.models;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;

public class EncryptedSearchDocumentKey {

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
}
