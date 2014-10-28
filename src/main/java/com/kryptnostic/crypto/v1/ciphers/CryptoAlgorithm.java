package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kryptnostic.crypto.v1.keys.PublicKeyAlgorithm;

public enum CryptoAlgorithm {
    AES(CipherConstants.AES_CIPHER),
    RSA(PublicKeyAlgorithm.RSA.getValue());
    
    private final String algorithm;

    private CryptoAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @JsonValue
    public String getValue() {
        return algorithm;
    }

    public static final CryptoAlgorithm fromString(String algorithm) {
        if (algorithm.equals(CipherConstants.AES_CIPHER)) {
            return AES;
        } else if( algorithm.equals( PublicKeyAlgorithm.RSA.getValue() ) ){
            return RSA;    
        } else {
            throw new InvalidParameterException("Unrecognized algorithm: " + algorithm);
        }
    }
}
