package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;

import com.kryptnostic.crypto.v1.keys.SecretKeyFactoryType;

public class CryptoService extends AbstractCryptoService {
    private static final int DEFAULT_PASSWORD_ITERATIONS = 65536;
    private final char[] password;
    private final int iterations;
    private final SecretKeyFactoryType secretKeyFactoryType;

    private transient SecretKeyFactory factory;

    public CryptoService(Cypher cypher, char[] password) {
        this(cypher, DEFAULT_PASSWORD_ITERATIONS, password, SecretKeyFactoryType.PBKDF2WithHmacSHA1);
    }

    public CryptoService(Cypher cypher, int iterations, char[] password, SecretKeyFactoryType secretKeyFactoryType) {
        super( cypher );
        this.password = password;
        this.iterations = iterations;
        this.secretKeyFactoryType = secretKeyFactoryType;
    }
    
    public BlockCiphertext encrypt(String plaintext) throws InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            InvalidParameterSpecException {
        return encrypt(StringUtils.getBytesUtf8(plaintext) ,Cyphers.generateSalt() );
    }

    public String decrypt(BlockCiphertext ciphertext) throws InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException,
            BadPaddingException {
        return StringUtils.newStringUtf8( decryptBytes( ciphertext ) );
    }

    @Override
    protected SecretKeySpec getSecretKeySpec(byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        factory = secretKeyFactoryType.getInstance();
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, cypher.getKeySize());
        SecretKey key = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getEncoded(), cypher.getCipherDescription().getAlgorithm()
                .getValue());
        return secretKeySpec;
    }
    
    @Override
    protected SecretKeySpec getSecretKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getSecretKeySpec( Cyphers.generateSalt() );
    }
    
    @Override
    protected void finalize() throws Throwable {
        // Zero out the password when GC.
        Arrays.fill(password, (char) 0);
        super.finalize();
    }
    
}
