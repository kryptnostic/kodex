package com.kryptnostic.heracles.v1.ciphers;

import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BlockCipher {
    AES(CipherConstants.AES_CIPHER);

    private final String algorithm;

    private BlockCipher(String algorithm) {
        this.algorithm = algorithm;
    }

    @JsonValue
    public String getValue() {
        return algorithm;
    }

    public static final BlockCipher fromString(String algorithm) {
        if (algorithm.equals(CipherConstants.AES_CIPHER)) {
            return AES;
        } else {
            throw new InvalidParameterException("Unrecognized algorithm: " + algorithm);
        }
    }
}
