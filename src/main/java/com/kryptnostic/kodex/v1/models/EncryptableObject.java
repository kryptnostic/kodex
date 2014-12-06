package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;
import com.kryptnostic.storage.v1.models.DocumentBlock;
import com.kryptnostic.storage.v1.models.DocumentMetadata;

public class EncryptableObject<T> implements Serializable {
    private static final long      serialVersionUID = -1449670245836179256L;
    private final DocumentMetadata metadata;
    private final DocumentBlock[]  blocks;
    private final ChunkingStrategy chunker;

    public EncryptableObject(
            @JsonProperty( Names.METADATA_FIELD ) DocumentMetadata metadata,
            @JsonProperty( Names.BLOCKS_FIELD ) DocumentBlock[] blocks ) {
        this.metadata = metadata;
        this.blocks = blocks;
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        return super.createEncrypted( ciphertext, className );
    }

    @Override
    protected boolean canDecryptWith( Kodex<String> kodex ) throws SecurityConfigurationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected Encryptable<T> encryptWith( Kodex<String> kodex ) throws JsonProcessingException,
            SecurityConfigurationException {
        return null;
    }

    @Override
    protected Encryptable<T> decryptWith( Kodex<String> kodex ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        return null;
    }
}
