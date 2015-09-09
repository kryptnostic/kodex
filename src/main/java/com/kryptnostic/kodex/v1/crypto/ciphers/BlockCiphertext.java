package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.crypto.Ciphertext;

/**
 * Holds the output of performing an AES encryption with {@link PasswordCryptoService}
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonInclude( Include.NON_NULL )
public class BlockCiphertext extends Ciphertext {
    private static final long      serialVersionUID       = 5566319942401654333L;
    private static final String    FIELD_IV               = "iv";
    private static final String    FIELD_SALT             = "salt";
    private static final String    FIELD_TAG              = "tag";
    private static final String    FIELD_ENCRYPTED_LENGTH = "length";

    private final byte[]           iv;
    private final byte[]           salt;
    private final Optional<byte[]> encryptedLength;
    private final Optional<byte[]> tag;

    public BlockCiphertext( byte[] iv, byte[] salt,
            byte[] contents ) {
        this( iv, salt, contents, Optional.<byte[]> absent(), Optional.<byte[]> absent() );
    }

    @JsonCreator
    public BlockCiphertext(
            @JsonProperty( FIELD_IV ) byte[] iv,
            @JsonProperty( FIELD_SALT ) byte[] salt,
            @JsonProperty( FIELD_CONTENTS ) byte[] contents,
            @JsonProperty( FIELD_ENCRYPTED_LENGTH ) Optional<byte[]> encryptedLength,
            @JsonProperty( FIELD_TAG ) Optional<byte[]> tag ) {
        super( contents, null );
        this.iv = iv;
        this.salt = salt;
        this.encryptedLength = encryptedLength;
        this.tag = tag;
    }

    @JsonProperty( FIELD_IV )
    public byte[] getIv() {
        return iv;
    }

    @JsonProperty( FIELD_SALT )
    public byte[] getSalt() {
        return salt;
    }

    @JsonProperty( FIELD_ENCRYPTED_LENGTH )
    public Optional<byte[]> getEncryptedLength() {
        return encryptedLength;
    }

    @JsonProperty( FIELD_TAG )
    public Optional<byte[]> getTag() {
        return tag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( tag.or( new byte[] {} ) );
        result = prime * result + Arrays.hashCode( encryptedLength.or( new byte[] {} ) );
        result = prime * result + Arrays.hashCode( iv );
        result = prime * result + Arrays.hashCode( salt );
        return result;
    }

    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        else if ( obj instanceof BlockCiphertext ) {
            BlockCiphertext other = (BlockCiphertext) obj;
            boolean ivEquals = Arrays.equals( iv, other.iv );
            boolean saltEquals = Arrays.equals( salt, other.salt );

            // Only true when both null or both the same actual object, which is checked above
            boolean encryptedLengthEquals = ( encryptedLength == other.encryptedLength );
            if ( ( encryptedLength != null ) && ( other.encryptedLength != null ) ) {
                encryptedLengthEquals = Arrays.equals( encryptedLength.or( new byte[] {} ),
                        other.encryptedLength.or( new byte[] {} ) );
            }

            boolean tagEquals = ( tag == other.tag );
            if ( ( tag != null ) & ( other.tag != null ) ) {
                tagEquals = Arrays.equals( tag.or( new byte[] {} ),
                        other.tag.or( new byte[] {} ) );
            }

            return ivEquals && saltEquals && encryptedLengthEquals && tagEquals;
        }
        return false;
    }
}
