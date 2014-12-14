package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.JacksonKodexMarshaller;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

/**
 * Note: Using {@link PasswordCryptoService} implementation, any String data to be encrypted MUST be in UTF_8
 */
public class AesEncryptable<T> extends Encryptable<T> {
    private static final long                                    serialVersionUID          = -5071733999235074270L;
    private static ObjectMapper                                  mapper                    = KodexObjectMapperFactory
                                                                                                   .getObjectMapper();
    private static JacksonKodexMarshaller<PasswordCryptoService> cryptoServiceKodexFactory = new JacksonKodexMarshaller<PasswordCryptoService>(
                                                                                                   PasswordCryptoService.class,
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
            @JsonProperty( Names.KEY_FIELD ) String keyId,
            @JacksonInject CryptoServiceLoader loader ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, keyId, loader );
    }

    @Override
    protected Encryptable<T> encryptWith( CryptoServiceLoader loader ) throws JsonProcessingException,
            SecurityConfigurationException {
        CryptoService crypto = null;
        BlockCiphertext encryptedData = null;
        BlockCiphertext encryptedClassName = null;
        try {
            crypto = loader.get( PasswordCryptoService.class.getCanonicalName() );
        } catch ( ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }

        encryptedData = crypto.encrypt( mapper.writeValueAsBytes( getData() ) );
        encryptedClassName = crypto.encrypt( StringUtils.getBytesUtf8( getClassName() ) );
        return new AesEncryptable<T>( encryptedData, encryptedClassName );
    }

    private void wrapSecurityConfigurationException( Exception e ) throws SecurityConfigurationException {
        throw new SecurityConfigurationException( "Error occurred while trying to encrypt or decrypt data.", e );
    }

    @Override
    protected Encryptable<T> decryptWith( CryptoServiceLoader loader ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        CryptoService crypto = null;
        String className = null;
        byte[] objectBytes = null;
        try {
            crypto = loader.get( PasswordCryptoService.class.getCanonicalName() );
        } catch ( ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }
        className = StringUtils.newStringUtf8( crypto.decryptBytes( (BlockCiphertext) getEncryptedClassName() ) );
        objectBytes = crypto.decryptBytes( (BlockCiphertext) getEncryptedData() );

        @SuppressWarnings( "unchecked" )
        T obj = mapper.<T> readValue( objectBytes, (Class<T>) Class.forName( className ) );
        return new AesEncryptable<T>( obj );
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        return new AesEncryptable<T>( (BlockCiphertext) ciphertext, (BlockCiphertext) className );
    }

    @Override
    protected boolean canDecryptWith( CryptoServiceLoader loader ) throws SecurityConfigurationException {
        if ( loader != null ) {
            try {
                return loader.get( keyId )!= null;
            } catch ( ExecutionException e ) {
                wrapSecurityConfigurationException( e );
            }
        }
        return false;
    }

}
