package com.kryptnostic.kodex.v1.authentication;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.StringUtils;

import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cyphers;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.SecretKeyFactoryType;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public final class CredentialFactory {
    private static final int ITERATIONS = 1000;
    private static final int KEY_SIZE   = 256;

    private CredentialFactory() {}

    public static CredentialPair generateCredentialPair( String password ) throws InvalidKeySpecException,
            NoSuchAlgorithmException, SecurityConfigurationException {
        byte[] salt = Cyphers.generateSalt( KEY_SIZE << 3 );
        return new CredentialPair( deriveCredential( password, salt ), encryptSalt( password, salt ) );
    }

    public static String deriveCredential( String password, BlockCiphertext encryptedSalt )
            throws InvalidKeySpecException, NoSuchAlgorithmException, SecurityConfigurationException {
        return deriveCredential( password, decryptSalt( password, encryptedSalt ) );
    }

    private static String deriveCredential( String password, byte[] salt ) throws InvalidKeySpecException,
            NoSuchAlgorithmException {
        SecretKeyFactory skf = SecretKeyFactoryType.PBKDF2WithHmacSHA1.getInstance();

        PBEKeySpec spec = new PBEKeySpec( Preconditions.checkNotNull( password, "Password cannot be null" )
                .toCharArray(), Preconditions.checkNotNull( salt, "Salt cannot be null" ), ITERATIONS, KEY_SIZE );
        SecretKey key = skf.generateSecret( spec );
        
        return StringUtils.newStringUtf8( key.getEncoded() );
    }

    private static BlockCiphertext encryptSalt( String password, byte[] salt ) throws SecurityConfigurationException {
        CryptoService cryptoService = new PasswordCryptoService( Preconditions.checkNotNull(
                password,
                "Password cannot be null" ) );
        return cryptoService.encrypt( Preconditions.checkNotNull( salt, "Encrypted salt cannot be null" ) );
    }

    private static byte[] decryptSalt( String password, BlockCiphertext encryptedSalt )
            throws SecurityConfigurationException {
        CryptoService cryptoService = new PasswordCryptoService( Preconditions.checkNotNull(
                password,
                "Password cannot be null" ) );
        return cryptoService
                .decryptBytes( Preconditions.checkNotNull( encryptedSalt, "Encrypted salt cannot be null" ) );
    }

    public static final class CredentialPair {
        private final String          credential;
        private final BlockCiphertext encryptedSalt;

        public CredentialPair( String credential, BlockCiphertext encryptedSalt ) {
            this.credential = credential;
            this.encryptedSalt = encryptedSalt;
        }

        public String getCredential() {
            return credential;
        }

        public BlockCiphertext getEncryptedSalt() {
            return encryptedSalt;
        }
    }
}
