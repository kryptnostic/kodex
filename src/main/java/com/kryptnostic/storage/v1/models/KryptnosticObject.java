package com.kryptnostic.storage.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

/**
 * A chunked encrypted string that can be used to store notes, messages, and documents
 * 
 * @author sinaiman
 */
public class KryptnosticObject {
    private static final long         serialVersionUID = -6861113361696076897L;

    private final ObjectMetadata    metadata;
    private final Encryptable<String> body;

    /**
     * @param data Plaintext string
     * @param metadata Document id + version
     */
    public KryptnosticObject( ObjectMetadata metadata, String data ) {
        this.body = new Encryptable<String>( data, metadata.getId() );
        this.metadata = metadata;
    }

    /**
     * @param id Document identifier
     * @param body Plaintext body
     * @return A new document with the specified id and body
     */
    public static KryptnosticObject fromIdAndBody( String id, String body ) {
        return new KryptnosticObject( new ObjectMetadata( id ), body );
    }

    /**
     * @param metadata Document id + version
     * @param body Encryptable representing document body
     */
    @JsonCreator
    public KryptnosticObject(
            @JsonProperty( Names.METADATA_FIELD ) ObjectMetadata metadata,
            @JsonProperty( Names.BODY_FIELD ) Encryptable<String> body ) {
        Preconditions.checkArgument( metadata.getId().equals( body.getCryptoServiceId() ) );
        this.body = body;
        this.metadata = metadata;
    }

    /**
     * @return Document id + version
     */
    @JsonProperty( Names.METADATA_FIELD )
    public ObjectMetadata getMetadata() {
        return metadata;
    }

    /**
     * @return Encryptable representing document body
     */
    public Encryptable<String> getBody() {
        return body;
    }

    /**
     * @param loader
     * @return
     * @throws ClassNotFoundException
     * @throws SecurityConfigurationException
     * @throws IOException
     */
    public KryptnosticObject encrypt( CryptoServiceLoader loader ) throws ClassNotFoundException,
            SecurityConfigurationException, IOException {
        Encryptable<String> encryptedBody = body.encrypt( loader );
        KryptnosticObject d = new KryptnosticObject( metadata, encryptedBody );
        return d;
    }

    public KryptnosticObject decrypt( CryptoServiceLoader loader ) throws ClassNotFoundException,
            SecurityConfigurationException, IOException {
        Encryptable<String> decryptedBody = body.decrypt( loader );
        KryptnosticObject d = new KryptnosticObject( metadata, decryptedBody );
        return d;
    }

    @Override
    public boolean equals( Object o ) {
        KryptnosticObject other = (KryptnosticObject) o;
        return other.metadata.equals( metadata );
    }

}
