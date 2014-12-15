package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.DocumentBlock;

/**
 * Note: Using {@link PasswordCryptoService} implementation, any String data to be encrypted MUST be in UTF_8
 */
public class AesEncryptable<T> extends Encryptable<T> {
    private static final long serialVersionUID = -5071733999235074270L;

    public AesEncryptable( T data ) {
        super( data );
    }

    public AesEncryptable( DocumentBlock[] ciphertext, BlockCiphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public AesEncryptable(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) DocumentBlock[] ciphertext,
            @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME ) BlockCiphertext className,
            @JsonProperty( Names.KEY_FIELD ) String keyId,
            @JacksonInject CryptoServiceLoader loader ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, keyId, loader );
    }

    @Override
    protected Encryptable<T> createEncrypted( DocumentBlock[] ciphertext, Ciphertext className ) {
        return new AesEncryptable<T>( ciphertext, (BlockCiphertext) className );
    }
}
