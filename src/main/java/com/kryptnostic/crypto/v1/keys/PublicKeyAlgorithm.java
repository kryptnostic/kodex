package com.kryptnostic.crypto.v1.keys;

public enum PublicKeyAlgorithm {
    RSA( "RSA" ),
    EC( "EC" );
    
    private final String value;
    
    private PublicKeyAlgorithm( String value ) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}