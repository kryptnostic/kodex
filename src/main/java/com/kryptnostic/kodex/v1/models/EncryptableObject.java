package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.DocumentMetadata;

public class EncryptableObject<T> extends AesEncryptable<T> {
    private static final long      serialVersionUID = -1449670245836179256L;
    private final DocumentMetadata metadata;
    private final DocumentBlock[]  blocks;

    public EncryptableObject(
            @JsonProperty( Names.METADATA_FIELD ) DocumentMetadata metadata,
            @JsonProperty( Names.BLOCKS_FIELD ) DocumentBlock[] blocks,
            @JsonProperty( Names.KEY_FIELD ) String keyId,
            @JacksonInject CryptoServiceLoader loader ) {
        super()
        this.metadata = metadata;
        this.blocks = blocks;
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        return super.createEncrypted( ciphertext, className );
    }


    private static class BlockDecrypter implements Function<DocumentBlock, byte[]> {
        private final AesCryptoService decrypter;

        public BlockDecrypter( AesCryptoService decrypter ) {
            this.decrypter = decrypter;
        }

        @Override
        public byte[] apply( DocumentBlock input ) {
            try {
                return decrypter.decryptBytes( input.getBlock() );
            } catch ( SecurityConfigurationException e ) {
                return null;
            }
        }

    }
}
