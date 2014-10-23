package com.kryptnostic.kodex.v1.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.v1.keys.SecretKeyFactoryType;

public final class IntermediateHashedCredential {
    private static final String CREDENTIAL_FIELD = "credential";
    private static final String SALT_FIELD = "salt";
    private static final int ITERATIONS = 1000;
    private static final int KEY_SIZE = 256;
    private final byte[] credential;
    private final byte[] salt;

    
    private IntermediateHashedCredential( byte[] credential , byte[] salt ) {
        this.credential = credential;
        this.salt = salt;
    }
    
    public IntermediateHashedCredential( String credential , byte[] localSalt, byte[] remoteSalt ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory skf = SecretKeyFactoryType.PBKDF2WithHmacSHA256.getInstance();
        PBEKeySpec spec = new PBEKeySpec( credential.toCharArray() , localSalt, ITERATIONS, KEY_SIZE );
        SecretKey key = skf.generateSecret(spec);
        this.credential = key.getEncoded();
        salt = remoteSalt;
    }

    @JsonProperty( CREDENTIAL_FIELD )
    public byte[] getCredential() {
        return credential;
    }
    
    @JsonProperty( SALT_FIELD )
    public byte[] getSalt() {
        return salt;
    }
    
    @JsonCreator
    public static IntermediateHashedCredential fromRawBytes( 
            @JsonProperty( CREDENTIAL_FIELD ) byte[] credential,
            @JsonProperty( SALT_FIELD ) byte[] salt 
            ) {
        return new IntermediateHashedCredential( credential , salt );
    }
}
