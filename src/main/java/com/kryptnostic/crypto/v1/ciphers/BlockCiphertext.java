package com.kryptnostic.crypto.v1.ciphers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.crypto.Ciphertext;

/**
 * Holds the output of performing an AES encryption with {@link CryptoService}
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public class BlockCiphertext extends Ciphertext {
    private static final String FIELD_IV = "iv";
    private static final String FIELD_SALT = "salt";
    private static final String FIELD_ENCRYPTED_LENGTH = "length";

    private final byte[] iv;
    private final byte[] salt;
    private final byte[] encryptedLength;

    @JsonCreator
    public BlockCiphertext(@JsonProperty(FIELD_IV) byte[] iv, @JsonProperty(FIELD_SALT) byte[] salt,
            @JsonProperty(FIELD_CONTENTS) byte[] contents, @JsonProperty(FIELD_ENCRYPTED_LENGTH) byte[] encryptedLength) {
        super(contents, null);
        this.iv = iv;
        this.salt = salt;
        this.encryptedLength = encryptedLength;
    }

    @JsonProperty(FIELD_IV)
    public byte[] getIv() {
        return iv;
    }

    @JsonProperty(FIELD_SALT)
    public byte[] getSalt() {
        return salt;
    }

    @JsonProperty(FIELD_ENCRYPTED_LENGTH)
    public byte[] getEncryptedLength() {
        return encryptedLength;
    }
}
