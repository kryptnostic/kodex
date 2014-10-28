package com.kryptnostic.crypto.v1.signatures;

public enum SignatureAlgorithm {
    SHA1withRSA( "SHA1" ),
    SHA256withRSA( "SHA256withRSA" ),
    SHA384withRSA( "SHA384withRSA" ),
    SHA512withRSA( "SHA512withRSA" ),
    SHA512withECDSA( "SHA512withECDSA" );
    
    private final String value;
    
    private SignatureAlgorithm( String value ) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString() {
        return value;
    }
}
