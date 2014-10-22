package com.kryptnostic.crypto.v1.keys;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class Keys {
    private Keys(){}
    
    public static final KeyPair generateRsaKeyPair( int keySize ) throws NoSuchAlgorithmException { 
        return generateKeyPair( PublicKeyAlgorithm.RSA , keySize );
    }
    
    public static final KeyPair generateEccKeyPair( int keySize ) throws NoSuchAlgorithmException {
        return generateKeyPair( PublicKeyAlgorithm.EC , keySize );
    }
    
    public static final KeyPair generateKeyPair( PublicKeyAlgorithm algorithm, int keySize ) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance( algorithm.getValue() );
        keyGen.initialize( keySize, new SecureRandom() );
        return keyGen.generateKeyPair();
    }
    
    public static PrivateKey privateKeyFromBytes( PublicKeyAlgorithm algorithm , byte[] bytes ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance(algorithm.getValue()).generatePrivate(new PKCS8EncodedKeySpec( bytes ));
    }
    
    public static PublicKey publicKeyFromBytes( PublicKeyAlgorithm algorithm, byte[] bytes ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance(algorithm.getValue()).generatePublic(new X509EncodedKeySpec(bytes));
    }
}
