package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Purposefully named type-safe wrapper for working with Java's insane crypto API.
 *
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */

public enum Cypher {
    AES_GCM_128( CryptoAlgorithm.AES, Mode.GCM, Padding.NONE, 128, false ),
    AES_GCM_128_SALTED( CryptoAlgorithm.AES, Mode.GCM, Padding.NONE, 128, true ),
    AES_CTR_128( CryptoAlgorithm.AES, Mode.CTR, Padding.NONE, 128, false ),
    AES_CTR_128_SALTED( CryptoAlgorithm.AES, Mode.CTR, Padding.NONE, 128, true ),
    AES_CTR_256( CryptoAlgorithm.AES, Mode.CTR, Padding.NONE, 256, false ),
    AES_CTR_256_SALTED( CryptoAlgorithm.AES, Mode.CTR, Padding.NONE, 256, true ),
    AES_CBC_PKCS5_128( CryptoAlgorithm.AES, Mode.CBC, Padding.PKCS5, 128, false ),
    AES_CBC_PKCS5_128_SALTED( CryptoAlgorithm.AES, Mode.CBC, Padding.PKCS5, 128, true ),
    AES_CBC_PKCS5_256( CryptoAlgorithm.AES, Mode.CBC, Padding.PKCS5, 256, false ),
    AES_CBC_PKCS5_256_SALTED( CryptoAlgorithm.AES, Mode.CBC, Padding.PKCS5, 256, true ),
    RSA_OAEP_SHA1_1024( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 1024, false ),
    RSA_OAEP_SHA1_1024_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 1024, true ),
    RSA_OAEP_SHA1_2048( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 2048, false ),
    RSA_OAEP_SHA1_2048_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 2048, true ),
    RSA_OAEP_SHA1_4096( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 4096, false ),
    RSA_OAEP_SHA1_4096_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA1AndMGF1Padding, 4096, true ),
    RSA_OAEP_SHA256_1024( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 1024, false ),
    RSA_OAEP_SHA256_1024_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 1024, true ),
    RSA_OAEP_SHA256_2048( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 2048, false ),
    RSA_OAEP_SHA256_2048_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 2048, true ),
    RSA_OAEP_SHA256_4096( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 4096, false ),
    RSA_OAEP_SHA256_4096_SALTED( CryptoAlgorithm.RSA, Mode.ECB, Padding.OAEPWithSHA256AndMGF1Padding, 4096, true );

    public static final Logger      logger          = LoggerFactory.getLogger( Cypher.class );

    public static final Cypher      DEFAULT         = AES_CTR_128;

    private static final String     CIPHER_ENCODING = "%s/%s/%s";
    private final CipherDescription description;
    private final boolean salted;

    private Cypher( CryptoAlgorithm algorithm, Mode mode, Padding padding, int keySize, boolean salted ) {
        this.salted = salted;
        description = new CipherDescription( algorithm, mode, padding, keySize );
    }

    @JsonValue
    public CipherDescription getCipherDescription() {
        return description;
    }

    public Cipher getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance( toString() );
    }

    @Override
    public String toString() {
        return String.format(
                CIPHER_ENCODING,
                description.getAlgorithm().getValue(),
                description.getMode().getValue(),
                description.getPadding().getValue() );
    }

    public int getKeySize() {
        return description.getKeySize();
    }

    public CryptoAlgorithm getAlgorithm() {
        return description.getAlgorithm();
    }

    public boolean isSalted() {
        return salted;
    }

    public String getName() {
        return getAlgorithm().getValue();
    }

    public KeyGenerator getKeyGenerator() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if ( description.getAlgorithm() == CryptoAlgorithm.AES ) {
            return KeyGenerator.getInstance( description.getAlgorithm().getValue() );
        }
        throw new InvalidAlgorithmParameterException( "Key generators are only supported for AES algorithm." );
    }

    @JsonCreator
    public static Cypher createCipher( String name ) {
        return valueOf( name );
    }

    // @JsonCreator
    public static Cypher createCipher( CipherDescription description ) {
        Preconditions.checkArgument(
                ImmutableSet.of( 128, 256, 1024, 2048, 4096 ).contains( description.getKeySize() ),
                "Only 128 bit and 256 key sizes are supported." );
        if ( description.getAlgorithm().equals( CryptoAlgorithm.AES ) ) {
            if ( description.getMode().equals( Mode.CTR ) ) {
                if ( description.getPadding().equals( Padding.NONE ) ) {
                    if ( description.getKeySize() == 128 ) {
                        return AES_CTR_128;
                    } else if ( description.getKeySize() == 256 ) {
                        return AES_CTR_256;
                    }
                } else if ( description.getPadding().equals( Padding.PKCS5 ) ) {
                    if ( description.getKeySize() == 128 ) {
                        return AES_CBC_PKCS5_128;
                    } else if ( description.getKeySize() == 256 ) {
                        return AES_CBC_PKCS5_256;
                    } else {
                        return unrecognizedCipher( "An unsupported key size was specified." );
                    }
                }
            }
        } else if ( description.getAlgorithm().equals( CryptoAlgorithm.RSA ) ) {
            if ( description.getMode().equals( Mode.ECB ) ) {
                switch ( description.getPadding() ) {
                    case OAEPWithSHA1AndMGF1Padding:
                        switch ( description.getKeySize() ) {
                            case 1024:
                                return RSA_OAEP_SHA1_1024;
                            case 2048:
                                return RSA_OAEP_SHA1_2048;
                            case 4096:
                                return RSA_OAEP_SHA1_4096;
                            default:
                                return unrecognizedCipher();
                        }
                    case OAEPWithSHA256AndMGF1Padding:
                        switch ( description.getKeySize() ) {
                            case 1024:
                                return RSA_OAEP_SHA256_1024;
                            case 2048:
                                return RSA_OAEP_SHA256_2048;
                            case 4096:
                                return RSA_OAEP_SHA256_4096;
                            default:
                                return unrecognizedCipher();
                        }
                    default:
                        return unrecognizedCipher();
                }
            }
        }
        return unrecognizedCipher();
    }

    private static final Cypher unrecognizedCipher() throws InvalidParameterException {
        return unrecognizedCipher( "An unsupported CipherDescription was received." );
    }

    private static final Cypher unrecognizedCipher( String additionalInfo ) {
        throw new InvalidParameterException( "Unrecognized cipher description: " + additionalInfo );
    }


}
