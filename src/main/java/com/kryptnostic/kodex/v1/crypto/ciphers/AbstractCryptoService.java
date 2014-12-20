package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

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

public abstract class AbstractCryptoService {
    protected final Cypher     cypher;

    protected static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;

    public AbstractCryptoService( Cypher cypher ) {
        this.cypher = cypher;
    }

    public BlockCiphertext encrypt( byte[] bytes ) throws SecurityConfigurationException {
        return encrypt( bytes, new byte[ 0 ] );
    }

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

            byte[] encryptedLength = null;

            // Add the encrypted length if padding mode is PKCS5
            if ( cypher.getCipherDescription().getPadding().equals( Padding.PKCS5 ) ) {
                byte[] lenBytes = new byte[ INTEGER_BYTES ];

                ByteBuffer lenBuf = ByteBuffer.wrap( lenBytes );
                lenBuf.putInt( bytes.length );

                encryptedLength = cipher.update( lenBytes );
            }

            byte[] encryptedBytes = cipher.doFinal( bytes );

            return new BlockCiphertext( iv, salt, encryptedBytes, encryptedLength );
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

    public byte[] decryptBytes( BlockCiphertext ciphertext ) throws SecurityConfigurationException {
        try {
            SecretKeySpec spec = getSecretKeySpec( ciphertext.getSalt() );
            Cipher cipher = cypher.getInstance();
            int length = 0;
            byte[] decryptedLength = null;
            byte[] plaintext;
            ByteBuffer buf;

            cipher.init( Cipher.DECRYPT_MODE, spec, new IvParameterSpec( ciphertext.getIv() ) );

            // Encrypted length is only null for modes that don't require padding.
            if ( ciphertext.getEncryptedLength() != null ) {
                decryptedLength = cipher.update( ciphertext.getEncryptedLength() );
            }

            byte[] rawBytes = cipher.doFinal( ciphertext.getContents() );

            if ( ciphertext.getEncryptedLength() == null ) {
                return rawBytes;
            } else if ( ( ciphertext.getEncryptedLength() != null ) && ( decryptedLength == null ) ) {
                buf = ByteBuffer.wrap( rawBytes );
                length = buf.getInt();
                plaintext = new byte[ length ];
                buf.get( plaintext );
            } else {
                length = ByteBuffer.wrap( decryptedLength ).getInt();
                plaintext = rawBytes;
            }

            if ( plaintext.length != length ) {
                return Arrays.copyOf( plaintext, length );
            }

            return plaintext;
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

    @JsonProperty( Names.CYPHER_FIELD )
    public Cypher getCypher() {
        return cypher;
    }

    @JsonIgnore
    protected abstract SecretKeySpec getSecretKeySpec( byte[] salt ) throws NoSuchAlgorithmException,
            InvalidKeySpecException;

}
