package com.kryptnostic.heracles.v1.ciphers;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Cyphers {
    private static final SecureRandom random;
    private static final Logger logger = LoggerFactory.getLogger(Cyphers.class);

    private Cyphers() {
    }

    static {
        random = new SecureRandom();
    }

    public static byte[] generateSalt() {
        return generateSalt(20);
    }

    public static byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return salt;
    }
}
