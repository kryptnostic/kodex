package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public final class Cyphers {
    private static final SecureRandom random;
    private static final Logger logger = LoggerFactory.getLogger(Cyphers.class);
    
    private Cyphers() {}

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
    
    public static SecretKey generateSecretKey( Cypher cypher ) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyGenerator generator = cypher.getKeyGenerator();
        generator.init( cypher.getKeySize());
        return generator.generateKey();
    }
    
    public static byte[] decrypt( Cypher cypher, PrivateKey key , byte[] ciphertext ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = cypher.getInstance();
        cipher.init( Cipher.DECRYPT_MODE , key );
        return  cipher.doFinal( Preconditions.checkNotNull( ciphertext , "Ciphertext cannot be null" ) );
    }

    public static byte[] encrypt( Cypher cypher, PublicKey key , byte [] secretKey ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = cypher.getInstance();
        cipher.init( Cipher.ENCRYPT_MODE , key );
        return cipher.doFinal( secretKey );
    }
}
