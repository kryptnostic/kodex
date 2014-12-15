package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.storage.v1.models.DocumentBlock;

public class FheEncryptable<T> extends Encryptable<T> {
    private static final long                         serialVersionUID       = -4740442054069941609L;
    private final static ObjectMapper                 mapper                 = KodexObjectMapperFactory
                                                                                     .getObjectMapper();

    //TODO: Re-implement FheEncryptable via an FheCryptoService.
    public FheEncryptable( T data ) {
        super( data );
    }
/*
    public FheEncryptable( Ciphertext ciphertext, Ciphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public FheEncryptable(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) Ciphertext ciphertext,
            @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME ) Ciphertext className,
            @JsonProperty( Names.KEY_FIELD ) String keyId,
            @JacksonInject CryptoServiceLoader loader ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        super( ciphertext, className, keyId, loader);
    }

    @Override
    protected Encryptable<T> encryptWith( CryptoServiceLoader loader ) throws SecurityConfigurationException {
        PublicKey key;
        Ciphertext encryptedData = null;
        Ciphertext encryptedClassName = null;
        try {
            key = loader.get( PublicKey.class.getCanonicalName() );
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
*/


    @Override
    protected Encryptable<T> createEncrypted( DocumentBlock[] ciphertext, Ciphertext className ) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected boolean canDecryptWith( CryptoServiceLoader kodex ) throws SecurityConfigurationException {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    protected Encryptable<T> encryptWith( CryptoServiceLoader loader ) throws JsonProcessingException,
            SecurityConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected Encryptable<T> decryptWith( CryptoServiceLoader loader ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected Iterable<byte[]> toUnencryptedBlocks() throws JsonProcessingException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected T fromBlocks( Iterable<byte[]> unencryptedBlocks, String className ) throws IOException,
            ClassNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
}
