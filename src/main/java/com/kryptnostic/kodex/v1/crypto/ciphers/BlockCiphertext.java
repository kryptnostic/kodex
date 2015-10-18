package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;

/**
 * Holds the output of performing an AES encryption with {@link PasswordCryptoService}
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
@JsonInclude( Include.NON_NULL )
public class BlockCiphertext extends Ciphertext {
    private static final long      serialVersionUID = 5566319942401654333L;

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
            @JsonProperty( Names.IV ) byte[] iv,
            @JsonProperty( Names.SALT ) byte[] salt,
            @JsonProperty( Names.CONTENTS ) byte[] contents,
            @JsonProperty( Names.ENCRYPTED_LENGTH ) Optional<byte[]> encryptedLength,
            @JsonProperty( Names.TAG ) Optional<byte[]> tag ) {
        super( contents, null );
        this.iv = iv;
        this.salt = salt;
        this.encryptedLength = encryptedLength;
        this.tag = tag;
    }

    @JsonProperty( Names.IV )
    public byte[] getIv() {
        return iv;
    }

    @JsonProperty( Names.SALT )
    public byte[] getSalt() {
        return salt;
    }

    @JsonProperty( Names.ENCRYPTED_LENGTH )
    public Optional<byte[]> getEncryptedLength() {
        return encryptedLength;
    }

    @JsonProperty( Names.TAG )
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
