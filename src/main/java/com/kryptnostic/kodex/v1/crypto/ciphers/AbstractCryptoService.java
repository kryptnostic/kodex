package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public abstract class AbstractCryptoService implements CryptoService {
    protected final Cypher     cypher;

    protected static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;

    public AbstractCryptoService( Cypher cypher ) {
        this.cypher = cypher;
    }

    /* (non-Javadoc)
     * @see com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService#encrypt(byte[])
     */
    @Override
    public BlockCiphertext encrypt( byte[] bytes ) throws SecurityConfigurationException {
        return encrypt( bytes, new byte[ 0 ] );
    }

    /* (non-Javadoc)
     * @see com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService#encrypt(byte[], byte[])
     */
    @Override
    public BlockCiphertext encrypt( byte[] bytes, byte[] salt ) throws SecurityConfigurationException {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec( salt );
            Cipher cipher = cypher.getInstance();
            byte[] iv;
            cipher.init( Cipher.ENCRYPT_MODE, secretKeySpec );
            AlgorithmParameters params = cipher.getParameters();
            if ( params == null ) {
                iv = Cyphers.generateSalt( cypher.getKeySize() >>> 3 );
                try {
                    cipher.init( Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec( iv ) );
                } catch ( InvalidAlgorithmParameterException e ) {
                    throw new SecurityConfigurationException( e );
                }
            } else {
                iv = params.getParameterSpec( IvParameterSpec.class ).getIV();
            }

            byte[] encryptedBytes = cipher.doFinal( bytes );
            return new BlockCiphertext( iv, salt, encryptedBytes, null );
        } catch ( NoSuchAlgorithmException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidKeySpecException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( IllegalBlockSizeException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( BadPaddingException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( NoSuchPaddingException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidKeyException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidParameterSpecException e ) {
            throw new SecurityConfigurationException( e );
        }
    }

    /* (non-Javadoc)
     * @see com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService#decryptBytes(com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext)
     */
    @Override
    public byte[] decryptBytes( BlockCiphertext ciphertext ) throws SecurityConfigurationException {
        try {
            SecretKeySpec spec = getSecretKeySpec( ciphertext.getSalt() );
            Cipher cipher = cypher.getInstance();

            cipher.init( Cipher.DECRYPT_MODE, spec, new IvParameterSpec( ciphertext.getIv() ) );
            return cipher.doFinal( ciphertext.getContents() );
        } catch ( NoSuchAlgorithmException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidKeySpecException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( IllegalBlockSizeException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( BadPaddingException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( NoSuchPaddingException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidKeyException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( InvalidAlgorithmParameterException e ) {
            throw new SecurityConfigurationException( e );
        }
    }

    /* (non-Javadoc)
     * @see com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService#getCypher()
     */
    @Override
    @JsonProperty( Names.CYPHER_FIELD )
    public Cypher getCypher() {
        return cypher;
    }

    @JsonIgnore
    protected abstract SecretKeySpec getSecretKeySpec( byte[] salt ) throws NoSuchAlgorithmException,
            InvalidKeySpecException;

}
