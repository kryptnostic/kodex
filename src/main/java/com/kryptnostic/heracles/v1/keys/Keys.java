package com.kryptnostic.heracles.v1.keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

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
    
}
