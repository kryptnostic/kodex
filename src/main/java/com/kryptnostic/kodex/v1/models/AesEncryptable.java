package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.keys.JacksonKodexMarshaller;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

/**
 * Note: Using {@link CryptoService} implementation, any String data to be encrypted MUST be in UTF_8
 */
public class AesEncryptable<T> extends Encryptable<T> {
    private static final long                            serialVersionUID          = -5071733999235074270L;
    private static ObjectMapper                          mapper                    = ( new KodexObjectMapperFactory() )
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

            encryptedData = crypto.encrypt( mapper.writeValueAsString( getData() ), Charsets.UTF_8.toString() );
            encryptedClassName = crypto.encrypt( getClassName(), Charsets.UTF_8.toString() );
        } catch ( InvalidKeyException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( InvalidAlgorithmParameterException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( NoSuchAlgorithmException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( NoSuchPaddingException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( InvalidKeySpecException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( IllegalBlockSizeException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( BadPaddingException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( SealedKodexException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( IOException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( InvalidParameterSpecException e ) {
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
        } catch ( InvalidKeyException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( InvalidAlgorithmParameterException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( NoSuchAlgorithmException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( NoSuchPaddingException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( InvalidKeySpecException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( IllegalBlockSizeException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( BadPaddingException e ) {
            wrapSecurityConfigurationException( e );
        } catch ( SealedKodexException e ) {
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

    // TODO: a little bit yucky. we should centralize this config
    public static void setObjectMapper( ObjectMapper mapper ) {
        AesEncryptable.mapper = mapper;
    }

}
