package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.crypto.keys.JacksonKodexMarshaller;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class FheEncryptable<T> extends Encryptable<T> {
    private static final long                         serialVersionUID       = -4740442054069941609L;
    private final static ObjectMapper                 mapper                 = KodexObjectMapperFactory
                                                                                     .getObjectMapper();
    private static JacksonKodexMarshaller<PublicKey>  publicKeyKodexFactory  = new JacksonKodexMarshaller<PublicKey>(
                                                                                     PublicKey.class,
                                                                                     mapper );
    private static JacksonKodexMarshaller<PrivateKey> privateKeyKodexFactory = new JacksonKodexMarshaller<PrivateKey>(

                                                                             PrivateKey.class, mapper );

    public FheEncryptable( T data ) {
        super( data );
    }

    public FheEncryptable( Ciphertext ciphertext, Ciphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public FheEncryptable(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) Ciphertext ciphertext,
            @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME ) Ciphertext className,
            @JacksonInject Kodex<String> kodex ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, kodex );
    }

    @Override
    protected Encryptable<T> encryptWith( Kodex<String> kodex ) throws SecurityConfigurationException {
        PublicKey key;
        Ciphertext encryptedData = null;
        Ciphertext encryptedClassName = null;
        try {
            key = kodex.getKey( PublicKey.class.getCanonicalName(), publicKeyKodexFactory );
            if ( key == null ) {
                throw new NullPointerException( "No public key found" );
            }
            encryptedData = key.encryptIntoEnvelope( mapper.writeValueAsBytes( getData() ) );
            encryptedClassName = key.encryptIntoEnvelope( getClassName().getBytes() );
            return new FheEncryptable<T>( encryptedData, encryptedClassName );
        } catch ( SealedKodexException | SecurityConfigurationException | KodexException | JsonProcessingException e ) {
            throw new SecurityConfigurationException( e );
        }
    }

    @Override
    protected Encryptable<T> decryptWith( Kodex<String> kodex ) throws SecurityConfigurationException {
        PrivateKey key;
        try {
            key = kodex.getKey( PrivateKey.class.getCanonicalName(), privateKeyKodexFactory );
            String className = StringUtils.newStringUtf8( key.decryptFromEnvelope( getEncryptedClassName() ) );
            byte[] objectBytes = key.decryptFromEnvelope( getEncryptedData() );
            @SuppressWarnings( "unchecked" )
            T obj = mapper.<T> readValue( objectBytes, (Class<T>) Class.forName( className ) );
            return new FheEncryptable<T>( obj );
        } catch (
                SealedKodexException
                | SecurityConfigurationException
                | KodexException
                | ClassNotFoundException
                | IOException e ) {
            throw new SecurityConfigurationException( e );
        }
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        return new FheEncryptable<T>( ciphertext, className );
    }

    @Override
    protected boolean canDecryptWith( Kodex<String> kodex ) {
        if ( kodex != null ) {
            try {
                return kodex.containsKey( PrivateKey.class.getCanonicalName() );
            } catch ( SealedKodexException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

}
