package com.kryptnostic.storage.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;

public class Document extends AesEncryptable<String> {
    private static final long      serialVersionUID = -8243618155514369238L;
    public static final String     FIELD_METADATA   = "metadata";
    public static final String     FIELD_BLOCKS     = "blocks";

    private final DocumentMetadata metadata;


    @JsonCreator
    public Document(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) DocumentBlock[] ciphertext,
            @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME ) BlockCiphertext className,
            @JsonProperty( Names.KEY_FIELD ) String keyId,
            @JacksonInject CryptoServiceLoader loader ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, keyId, loader );
        this.metadata = new DocumentMetadata( keyId, ciphertext.length );
    }

    @JsonProperty( FIELD_BLOCKS )
    public DocumentBlock[] getBlocks() {
        return blocks;
    }

    @JsonProperty( FIELD_METADATA )
    public DocumentMetadata getMetadata() {
        return metadata;
    }

    /**
     * @return The decrypted document text
     */
    @JsonIgnore
    public String getBody( Kodex<String> kodex ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

    /**
     * This does not check for block content equality because blocks are encrypted This uses the verification hash,
     * which is not guaranteed to be consistent with the document blocks
     */
    @Override
    public boolean equals( Object o ) {
        Document d = (Document) o;
        return ( blocks.length == d.blocks.length ) && metadata.equals( d.metadata );
    }
}
