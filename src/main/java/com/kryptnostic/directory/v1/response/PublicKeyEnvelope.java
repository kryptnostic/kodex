package com.kryptnostic.directory.v1.response;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.v1.keys.Keys;
import com.kryptnostic.crypto.v1.keys.PublicKeyAlgorithm;

public class PublicKeyEnvelope {
    private static final String PUBLIC_KEY_FIELD = "publicKey";

    private final byte[]        publicKey;

    public PublicKeyEnvelope( PublicKey publicKey ) {
        this( publicKey.getEncoded() );
    }

    @JsonCreator
    public PublicKeyEnvelope( @JsonProperty( PUBLIC_KEY_FIELD ) byte[] publicKey ) {
        this.publicKey = publicKey;
    }

    @JsonProperty( PUBLIC_KEY_FIELD )
    public byte[] getBytes() {
        return publicKey;
    }

    public PublicKey asRsaPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return Keys.publicKeyFromBytes( PublicKeyAlgorithm.RSA, publicKey );
    }

    public PublicKey asEccPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return Keys.publicKeyFromBytes( PublicKeyAlgorithm.EC, publicKey );
    }
}
