package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.keys.JacksonKodexMarshaller;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

/**
 * Note: Using {@link CryptoService} implementation, any String data to be encrypted MUST be in UTF_8
 */
public class AesEncryptable<T> extends Encryptable<T> {
    private static final long                            serialVersionUID          = -5071733999235074270L;
    private static ObjectMapper                          mapper                    = KodexObjectMapperFactory
                                                                                           .getObjectMapper();
    private static JacksonKodexMarshaller<CryptoService> cryptoServiceKodexFactory = new JacksonKodexMarshaller<CryptoService>(
                                                                                           CryptoService.class,
                                                                                           mapper );

    public AesEncryptable( T data ) {
        super( data );
    }

    public AesEncryptable( BlockCiphertext ciphertext, BlockCiphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public AesEncryptable(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) BlockCiphertext ciphertext,
            @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME ) BlockCiphertext className,
            @JacksonInject Kodex<String> kodex ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, kodex );
    }

    @Override
    protected Encryptable<T> encryptWith( Kodex<String> kodex ) throws JsonProcessingException,
            SecurityConfigurationException {
        CryptoService crypto;
        BlockCiphertext encryptedData = null;
        BlockCiphertext encryptedClassName = null;
        try {
            crypto = kodex.getKey( CryptoService.class.getCanonicalName(), cryptoServiceKodexFactory );

            encryptedData = crypto.encrypt( mapper.writeValueAsString( getData() ) );
            encryptedClassName = crypto.encrypt( getClassName() );
        } catch ( SealedKodexException | KodexException e ) {
            wrapSecurityConfigurationException( e );
        }
        return new AesEncryptable<T>( encryptedData, encryptedClassName );
    }

    private void wrapSecurityConfigurationException( Exception e ) throws SecurityConfigurationException {
        throw new SecurityConfigurationException( "Error occurred while trying to encrypt or decrypt data.", e );
    }

    @Override
    protected Encryptable<T> decryptWith( Kodex<String> kodex ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        CryptoService crypto;
        String className = null;
        String objectString = null;
        try {
            crypto = kodex.getKey( CryptoService.class.getCanonicalName(), cryptoServiceKodexFactory );
            className = crypto.decrypt( (BlockCiphertext) getEncryptedClassName() );
            objectString = crypto.decrypt( (BlockCiphertext) getEncryptedData() );

        } catch ( SealedKodexException | KodexException e ) {
            wrapSecurityConfigurationException( e );
        }

        @SuppressWarnings( "unchecked" )
        T obj = mapper.<T> readValue( objectString, (Class<T>) Class.forName( className ) );
        return new AesEncryptable<T>( obj );
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        return new AesEncryptable<T>( (BlockCiphertext) ciphertext, (BlockCiphertext) className );
    }

    @Override
    protected boolean canDecryptWith( Kodex<String> kodex ) throws SecurityConfigurationException {
        if ( kodex != null ) {
            try {
                return kodex.containsKey( CryptoService.class.getCanonicalName() );
            } catch ( SealedKodexException e ) {
                wrapSecurityConfigurationException( e );
            }
        }
        return false;
    }

}
