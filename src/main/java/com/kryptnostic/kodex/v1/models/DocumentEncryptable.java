package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.DocumentBlock;

public class DocumentEncryptable extends AesEncryptable<String> {
    private static final long serialVersionUID = -8889249885528630776L;

    public DocumentEncryptable( String data ) {
        super( data );
    }

    public DocumentEncryptable( DocumentBlock[] ciphertext, BlockCiphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public DocumentEncryptable(
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
    
   //TODO: Overwrite toUnencryptedBlocks and fromUnencryptedBlocks methods.
}
