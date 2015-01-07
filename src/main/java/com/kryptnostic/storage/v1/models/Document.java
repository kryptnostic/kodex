package com.kryptnostic.storage.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.crypto.DefaultChunkingStrategy;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

public class Document extends Encryptable<String> {

    public Document( String data ) {
        super( data );
    }

    @JsonCreator
    public Document(
            @JsonProperty( Names.DATA_FIELD ) EncryptableBlock[] ciphertext,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext className,
            @JsonProperty( Names.KEY_FIELD ) String cryptoServiceId,
            @JacksonInject CryptoServiceLoader loader ) throws SecurityConfigurationException,
            ClassNotFoundException,
            IOException {
        super( ciphertext, className, cryptoServiceId, loader, new DefaultChunkingStrategy() );
    }

}
