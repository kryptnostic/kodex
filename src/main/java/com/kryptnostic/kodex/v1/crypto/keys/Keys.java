package com.kryptnostic.kodex.v1.crypto.keys;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.kryptnostic.kodex.v1.crypto.signatures.SignatureAlgorithm;
import com.kryptnostic.kodex.v1.crypto.signatures.Signatures;

public final class Keys {
    private Keys() {}

    public static final KeyPair generateRsaKeyPair( int keySize ) throws NoSuchAlgorithmException {
        return generateKeyPair( PublicKeyAlgorithm.RSA, keySize );
    }

    public static final KeyPair generateEccKeyPair( int keySize ) throws NoSuchAlgorithmException {
        return generateKeyPair( PublicKeyAlgorithm.EC, keySize );
    }

    public static final KeyPair generateKeyPair( PublicKeyAlgorithm algorithm, int keySize )
            throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance( algorithm.getValue() );
        keyGen.initialize( keySize, new SecureRandom() );
        return keyGen.generateKeyPair();
    }

    public static byte[] sign( PrivateKey privateKey, SignatureAlgorithm algorithm, byte[]... data )
            throws SignatureException, InvalidKeyException {
        Signature signer = Signatures.createSigner( algorithm );
        signer.initSign( privateKey );
        for ( byte[] datum : data ) {
            signer.update( datum );
        }
        return signer.sign();
    }

    public static boolean verify( PublicKey publicKey, SignatureAlgorithm algorithm, byte[] signature, byte[]... data )
            throws SignatureException, InvalidKeyException {
        Signature signer = Signatures.createSigner( algorithm );
        signer.initVerify( publicKey );
        for ( byte[] datum : data ) {
            signer.update( datum );
        }
        return signer.verify( signature );
    }

    public static PublicKey publicKeyFromPrivateKey( PrivateKey privateKey ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String algorithm = privateKey.getAlgorithm();
        KeyFactory factory = KeyFactory.getInstance( algorithm );
        if ( algorithm.equals( PublicKeyAlgorithm.RSA.getValue() ) ) {
            RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                    rsaPrivateKey.getModulus(),
                    rsaPrivateKey.getPublicExponent() );
            return factory.generatePublic( publicKeySpec );
        }
        return null;
    }

    public static PrivateKey privateKeyFromBytes( PublicKeyAlgorithm algorithm, byte[] bytes )
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance( algorithm.getValue() ).generatePrivate( new PKCS8EncodedKeySpec( bytes ) );
    }

    public static PublicKey publicKeyFromBytes( PublicKeyAlgorithm algorithm, byte[] bytes )
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance( algorithm.getValue() ).generatePublic( new X509EncodedKeySpec( bytes ) );
    }
}
