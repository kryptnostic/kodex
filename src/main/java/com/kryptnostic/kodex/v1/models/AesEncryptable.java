package com.kryptnostic.kodex.v1.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.storage.v1.models.DocumentBlock;

/**
 * Note: Using {@link PasswordCryptoService} implementation, any String data to be encrypted MUST be in UTF_8
 */
public class AesEncryptable<T> extends Encryptable<T> {
    private static final long   serialVersionUID      = -5071733999235074270L;
    private static final int    BLOCK_LENGTH_IN_BYTES = 4096;
    private static ObjectMapper mapper                = KodexObjectMapperFactory.getObjectMapper();

    public AesEncryptable( T data ) {
        super( data );
    }

    public AesEncryptable( DocumentBlock[] ciphertext, BlockCiphertext className ) {
        super( ciphertext, className );
    }

    @JsonCreator
    public AesEncryptable(
            @JsonProperty( FIELD_ENCRYPTED_DATA ) DocumentBlock[] ciphertext,
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
        BlockCiphertext encryptedClassName = null;
        try {
            crypto = loader.get( PasswordCryptoService.class.getCanonicalName() );
        } catch ( ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }
        
        List<BlockCiphertext> ciphertextBlocks = Lists.newArrayList();
        
        for( byte[] block : toUnencryptedBlocks() ) {
            ciphertextBlocks.add( crypto.encrypt( block ) );
        }
        
        int total = ciphertextBlocks.size();
        DocumentBlock[] blocks = new DocumentBlock[ total ];
        for( int i = 0 ; i < ciphertextBlocks.size(); ++i ) {
            //TODO: Actually compute the hash.
            blocks[ i ] = new DocumentBlock( ciphertextBlocks.get( i ), "" , total, i );
        }
        
        encryptedClassName = crypto.encrypt( StringUtils.getBytesUtf8( getClassName() ) );
        return new AesEncryptable<T>( blocks, encryptedClassName );
    }

    private void wrapSecurityConfigurationException( Exception e ) throws SecurityConfigurationException {
        throw new SecurityConfigurationException( "Error occurred while trying to encrypt or decrypt data.", e );
    }

    @Override
    protected Encryptable<T> decryptWith( CryptoServiceLoader loader ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        CryptoService crypto = null;
        String className = null;
        try {
            crypto = loader.get( PasswordCryptoService.class.getCanonicalName() );
        } catch ( ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }
        className = StringUtils.newStringUtf8( crypto.decryptBytes( (BlockCiphertext) getEncryptedClassName() ) );
        return new AesEncryptable<T>( fromBlocks(
                Iterables.transform( Arrays.asList( getEncryptedData() ), new BlockDecrypter( crypto ) ),
                className ) );
    }

    @Override
    protected Encryptable<T> createEncrypted( DocumentBlock[] ciphertext, Ciphertext className ) {
        return new AesEncryptable<T>( ciphertext, (BlockCiphertext) className );
    }

    @Override
    protected boolean canDecryptWith( CryptoServiceLoader loader ) throws SecurityConfigurationException {
        if ( loader != null ) {
            try {
                return loader.get( keyId ) != null;
            } catch ( ExecutionException e ) {
                wrapSecurityConfigurationException( e );
            }
        }
        return false;
    }

    @Override
    protected Iterable<byte[]> toUnencryptedBlocks() throws JsonProcessingException {
        byte[] unencryptedData = mapper.writeValueAsBytes( getData() );
        List<byte[]> byteBlocks = Lists.newArrayListWithCapacity( ( unencryptedData.length / BLOCK_LENGTH_IN_BYTES )
                + ( ( unencryptedData.length % BLOCK_LENGTH_IN_BYTES ) == 0 ? 0 : 1 ) );
        ByteBuffer plaintext = ByteBuffer.wrap( mapper.writeValueAsBytes( unencryptedData ) );
        int remaining = plaintext.remaining();
        while ( remaining > 0 ) {
            byte[] block; 
            //Re-allocate byte block each time as it will be handed off to list.
            if( remaining >= BLOCK_LENGTH_IN_BYTES ) {
                block = new byte[ BLOCK_LENGTH_IN_BYTES ];
            } else {
                block = new byte[ remaining ]; 
            }
            plaintext.get( block );
            byteBlocks.add( block );
            remaining = plaintext.remaining();
        }
        
        return byteBlocks;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    protected T fromBlocks( Iterable<byte[]> unencryptedBlocks, String className ) throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for ( byte[] b : unencryptedBlocks ) {
            baos.write( b );
        }
        return mapper.<T> readValue( baos.toByteArray(), (Class<T>) Class.forName( className ) );
    }

    private static class BlockDecrypter implements Function<DocumentBlock, byte[]> {
        private static final Logger logger = LoggerFactory.getLogger( BlockDecrypter.class );
        private final CryptoService service;

        public BlockDecrypter( CryptoService service ) {
            this.service = service;
        }

        @Override
        public byte[] apply( DocumentBlock input ) {
            try {
                return service.decryptBytes( input.getBlock() );
            } catch ( SecurityConfigurationException e ) {
                logger.error( "Unable to decrypt block {} of {}.", input.getIndex(), input.getTotal() );
                return null;
            }
        }

    }
}
