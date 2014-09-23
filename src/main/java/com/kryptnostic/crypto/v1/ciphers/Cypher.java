package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

/**
 * Purposefully named type-safe wrapper for working with Java's insane crypto API.
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public enum Cypher {
    AES_CTR_128( BlockCipher.AES , Mode.CTR , Padding.NONE, 128 ),
    AES_CTR_256( BlockCipher.AES , Mode.CTR , Padding.NONE, 256 ),
    AES_CTR_PKCS5_128( BlockCipher.AES , Mode.CTR , Padding.PKCS5, 128 ),
    AES_CTR_PKCS5_256( BlockCipher.AES , Mode.CTR , Padding.PKCS5, 256 );
    
    private static final String CIPHER_ENCODING = "%s/%s/%s";
    private final CipherDescription description;
    
    private Cypher( BlockCipher algorithm , Mode mode , Padding padding, int keySize ) {
        description = new CipherDescription( algorithm , mode , padding , keySize );
    }
    
    @JsonValue 
    public CipherDescription getCipherDescription() {
        return description;
    }
    
    public Cipher getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance( toString() );
    }
    
    public String toString() {
        return String.format( CIPHER_ENCODING , description.getAlgorithm().getValue() , description.getMode().getValue() , description.getPadding().getValue() );
    }
    
    public int getKeySize() {
        return description.getKeySize();
    }
    
    @JsonCreator
    public static Cypher createCipher( CipherDescription description ) {
        Preconditions.checkArgument( description.getKeySize() == 128 || description.getKeySize() == 256 , "Only 128 bit and 256 key sizes are supported." );
        if( description.getAlgorithm().equals( BlockCipher.AES ) ) {
            if( description.getMode().equals( Mode.CTR ) ) {
                if( description.getPadding().equals( Padding.NONE ) ) {
                    if( description.getKeySize() == 128 ) {
                        return AES_CTR_128;
                    } else if ( description.getKeySize() == 256 ) {
                        return AES_CTR_256; 
                    } 
                } else if( description.getPadding().equals( Padding.PKCS5 ) ) {
                    if( description.getKeySize() == 128 ) {
                        return AES_CTR_PKCS5_128;
                    } else if ( description.getKeySize() == 256 ) {
                        return AES_CTR_PKCS5_256; 
                    } else {
                        return unrecognizedCipher("An unsupported key size was specified.");
                    }
                } else {
                    return unrecognizedCipher();
                }
            } else {
                return unrecognizedCipher();
            }
        }
        return unrecognizedCipher();
    }
    
    private static final Cypher unrecognizedCipher() throws InvalidParameterException { 
        return unrecognizedCipher("An unsupported CipherDescription was received.");
    }
    
    private static final Cypher unrecognizedCipher( String additionalInfo ) {
        throw new InvalidParameterException( "Unrecognized cipher description: " + additionalInfo );
    }
}
