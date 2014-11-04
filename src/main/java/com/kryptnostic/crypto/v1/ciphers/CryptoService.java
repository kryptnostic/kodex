package com.kryptnostic.crypto.v1.ciphers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.v1.keys.SecretKeyFactoryType;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class CryptoService extends AbstractCryptoService {
    private static final String        FACTORY_TYPE_FIELD          = "keyDerivationAlgorithm";
    private static final String        ITERATIONS_FIELD            = "iterations";
    private static final int           DEFAULT_PASSWORD_ITERATIONS = 128;
    
    private final char[]               password;
    private final int                  iterations;
    private final SecretKeyFactoryType secretKeyFactoryType;

    @JsonCreator
    public CryptoService(
            @JsonProperty( Names.CYPHER_FIELD ) CipherDescription description,
            @JsonProperty( Names.PASSWORD_FIELD ) char[] password,
            @JsonProperty( FACTORY_TYPE_FIELD ) SecretKeyFactoryType secretKeyFactoryType,
            @JsonProperty( ITERATIONS_FIELD ) int iterations ) {
        this( Cypher.createCipher( description ), iterations, password, secretKeyFactoryType );
    }

    public CryptoService( Cypher cypher, char[] password ) {
        this( cypher, DEFAULT_PASSWORD_ITERATIONS, password, SecretKeyFactoryType.PBKDF2WithHmacSHA1 );
    }

    public CryptoService( Cypher cypher, int iterations, char[] password, SecretKeyFactoryType secretKeyFactoryType ) {
        super( cypher );
        this.password = password;
        this.iterations = iterations;
        this.secretKeyFactoryType = secretKeyFactoryType;
    }

    public BlockCiphertext encrypt( byte[] plaintext ) throws SecurityConfigurationException {
        return encrypt( plaintext, Cyphers.generateSalt() );
    }

    /**
     * Note: plaintext String MUST be UTF_8 encoded.
     */
    public BlockCiphertext encrypt( String plaintext ) throws SecurityConfigurationException {
        return encrypt( StringUtils.getBytesUtf8( plaintext ), Cyphers.generateSalt() );
    }

    public String decrypt( BlockCiphertext ciphertext ) throws SecurityConfigurationException {
        return StringUtils.newStringUtf8( decryptBytes( ciphertext ) );
    }

    @JsonProperty( ITERATIONS_FIELD )
    public int getIterations() {
        return iterations;
    }

    @JsonProperty( FACTORY_TYPE_FIELD )
    public SecretKeyFactoryType getSecretKeyFactoryType() {
        return secretKeyFactoryType;
    }

    @JsonProperty( Names.PASSWORD_FIELD )
    public char[] getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    protected SecretKeySpec getSecretKeySpec( byte[] salt ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = secretKeyFactoryType.getInstance();
        PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, cypher.getKeySize() );
        SecretKey key = factory.generateSecret( spec );
        SecretKeySpec secretKeySpec = new SecretKeySpec( key.getEncoded(), cypher.getCipherDescription().getAlgorithm()
                .getValue() );
        return secretKeySpec;
    }

    @JsonIgnore
    protected SecretKeySpec getSecretKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return getSecretKeySpec( Cyphers.generateSalt() );
    }

    @Override
    protected void finalize() throws Throwable {
        // Zero out the password when GC.
        Arrays.fill( password, (char) 0 );
        super.finalize();
    }
}
