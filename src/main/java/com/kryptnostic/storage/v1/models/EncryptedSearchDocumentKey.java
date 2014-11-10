package com.kryptnostic.storage.v1.models;

import java.io.Serializable;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.users.v1.UserKey;

public class EncryptedSearchDocumentKey implements Serializable {
    private static final long              serialVersionUID   = -1727973926753941167L;
    public static final String             FIELD_SEARCH_NONCE = "searchNonce";
    public static final String             FIELD_BRIDGE_KEY   = "bridgeKey";

    private final BitVector                searchNonce;
    private final EncryptedSearchBridgeKey bridgeKey;
    private final UserKey                  user;

    @JsonCreator
    public EncryptedSearchDocumentKey(
            @JsonProperty( FIELD_SEARCH_NONCE ) BitVector searchNonce,
            @JsonProperty( FIELD_BRIDGE_KEY ) EncryptedSearchBridgeKey bridgeKey,
            @JsonProperty( Names.USER_FIELD ) UserKey user ) {
        this.searchNonce = searchNonce;
        this.bridgeKey = bridgeKey;
        this.user = user;
    }

    @JsonProperty( Names.USER_FIELD ) 
    public UserKey getUser() {
        return user;
    }

    @JsonProperty( FIELD_SEARCH_NONCE )
    public BitVector getSearchNonce() {
        return searchNonce;
    }

    @JsonProperty( FIELD_BRIDGE_KEY )
    public EncryptedSearchBridgeKey getBridgeKey() {
        return bridgeKey;
    }

    @Override
    public boolean equals( Object o ) {
        EncryptedSearchDocumentKey other = (EncryptedSearchDocumentKey) o;
        return searchNonce.equals( other.searchNonce ) && bridgeKey.equals( other.bridgeKey );
    }
}
