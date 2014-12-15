package com.kryptnostic.kodex.v1.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
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
 * Note: It doesn't make sense to override equals because the encrypted bytes are random
 * 
 * @author sinaiman
 *
 * @param <T> The type of the wrapped data you want to encrypt
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = As.PROPERTY,
    property = Encryptable.FIELD_CLASS )
public abstract class Encryptable<T> implements Serializable {
    private static final long        serialVersionUID           = 5128167833341065251L;
    protected static ObjectMapper    mapper                     = KodexObjectMapperFactory.getObjectMapper();
    protected static final int       BLOCK_LENGTH_IN_BYTES      = 4096;
    public static final HashFunction hashFunction               = Hashing.sha256();
    public static final String       FIELD_CLASS                = "@class";
    public static final String       FIELD_ENCRYPTED_CLASS_NAME = "name";
    public static final String       FIELD_ENCRYPTED_DATA       = "data";

    @JsonIgnore
    private final boolean            encrypted;
    @JsonIgnore
    private final T                  data;
    @JsonIgnore
    private final String             className;
    @JsonProperty( FIELD_ENCRYPTED_DATA )
    protected final DocumentBlock[]  encryptedData;
    @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME )
    protected final Ciphertext       encryptedClassName;
    @JsonProperty( Names.KEY_FIELD )
    protected final String           keyId;
    @JsonIgnore
    protected transient ByteBuffer   plaintext;

    public Encryptable( T data ) {
        this.encrypted = false;
        this.data = data;
        this.className = data.getClass().getName();
        this.encryptedData = null;
        this.encryptedClassName = null;
        this.keyId = PasswordCryptoService.class.getCanonicalName();
    }

    public Encryptable( DocumentBlock[] ciphertext, Ciphertext className ) {
        this.encrypted = true;
        this.data = null;
        this.className = null;
        this.encryptedData = ciphertext;
        this.encryptedClassName = className;
        this.keyId = PasswordCryptoService.class.getCanonicalName();
    }

    public Encryptable( DocumentBlock[] ciphertext, Ciphertext className, String keyId, CryptoServiceLoader loader ) throws JsonParseException,
            JsonMappingException,
            IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        if ( canDecryptWith( loader ) ) {
            Encryptable<T> encrypted = createEncrypted( ciphertext, className );
            Encryptable<T> decrypted = encrypted.decryptWith( loader );
            this.encrypted = false;
            this.data = decrypted.getData();
            this.className = decrypted.getClassName();
            this.encryptedData = null;
            this.encryptedClassName = null;
            this.keyId = keyId;
        } else {
            this.encrypted = true;
            this.data = null;
            this.className = null;
            this.encryptedData = ciphertext;
            this.encryptedClassName = className;
            this.keyId = keyId;
        }
    }

    public final Encryptable<T> encrypt( CryptoServiceLoader loader ) throws JsonProcessingException,
            SecurityConfigurationException {
        if ( this.encrypted ) {
            return this;
        }
        Preconditions.checkNotNull( this.data );
        Preconditions.checkNotNull( this.className );
        Preconditions.checkState( this.encryptedData == null );
        Preconditions.checkState( this.encryptedClassName == null );

        return encryptWith( loader );
    }

    protected Encryptable<T> encryptWith( CryptoServiceLoader loader ) throws JsonProcessingException,
            SecurityConfigurationException {
        CryptoService crypto = null;
        BlockCiphertext encryptedClassName = null;
        try {
            crypto = loader.get( getKeyId() );
        } catch ( ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }

        int total = getBlockCount();
        List<BlockCiphertext> ciphertextBlocks = Lists.newArrayListWithCapacity( total );

        for ( byte[] block : toUnencryptedBlocks() ) {
            ciphertextBlocks.add( crypto.encrypt( block ) );
        }

        Preconditions.checkState( ciphertextBlocks.size() == total, "Block count doesn't match iterable length!." );

        DocumentBlock[] blocks = new DocumentBlock[ total ];
        for ( int i = 0; i < total; ++i ) {
            BlockCiphertext ciphertext = ciphertextBlocks.get( i );
            blocks[ i ] = new DocumentBlock(
                    ciphertext,
                    hashFunction.hashBytes( ciphertext.getContents() ).asBytes(),
                    total,
                    i );
        }

        encryptedClassName = crypto.encrypt( StringUtils.getBytesUtf8( getClassName() ) );
        return createEncrypted( blocks, encryptedClassName );
    }

    public final Encryptable<T> decrypt( CryptoServiceLoader loader ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        if ( !this.encrypted ) {
            return this;
        }
        Preconditions.checkState( this.data == null );
        Preconditions.checkState( this.className == null );
        Preconditions.checkNotNull( this.encryptedData );
        Preconditions.checkNotNull( this.encryptedClassName );

        return decryptWith( loader );
    }

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
        // Lazy evaluated transformation to re-construct object.
        return new AesEncryptable<T>( fromBlocks(
                Iterables.transform( Arrays.asList( getEncryptedData() ), new BlockDecrypter( crypto ) ),
                className ) );
    }

    protected Iterable<byte[]> toUnencryptedBlocks() throws JsonProcessingException {
        ByteBuffer plaintext = ByteBuffer.wrap( mapper.writeValueAsBytes( getData() ) );

        int remaining = plaintext.remaining();

        List<byte[]> byteBlocks = Lists.newArrayListWithCapacity( getBlockCount() );
        while ( remaining > 0 ) {
            byte[] block;
            // Re-allocate byte block each time as it will be handed off to list.
            if ( remaining >= BLOCK_LENGTH_IN_BYTES ) {
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

    public int getBlockCount() throws JsonProcessingException {
        if ( plaintext == null ) {
            plaintext = ByteBuffer.wrap( mapper.writeValueAsBytes( getData() ) );
        }
        int remaining = plaintext.remaining();
        return ( remaining / BLOCK_LENGTH_IN_BYTES ) + ( ( remaining % BLOCK_LENGTH_IN_BYTES ) == 0 ? 0 : 1 );
    }

    @JsonIgnore
    public T getData() {
        return data;
    }

    @JsonIgnore
    public String getClassName() {
        return className;
    }

    @JsonProperty( FIELD_ENCRYPTED_DATA )
    public DocumentBlock[] getEncryptedData() {
        return encryptedData;
    }

    @JsonProperty( FIELD_ENCRYPTED_CLASS_NAME )
    public Ciphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonIgnore
    public boolean isEncrypted() {
        return encrypted;
    }

    protected String getKeyId() {
        return keyId;
    }

    protected void wrapSecurityConfigurationException( Exception e ) throws SecurityConfigurationException {
        throw new SecurityConfigurationException( "Error occurred while trying to encrypt or decrypt data.", e );
    }

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

    protected static class BlockDecrypter implements Function<DocumentBlock, byte[]> {
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

    @SuppressWarnings( "unchecked" )
    protected T fromBlocks( Iterable<byte[]> unencryptedBlocks, String className ) throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for ( byte[] b : unencryptedBlocks ) {
            baos.write( b );
        }
        return mapper.<T> readValue( baos.toByteArray(), (Class<T>) Class.forName( className ) );
    }

    protected abstract Encryptable<T> createEncrypted( DocumentBlock[] ciphertext, Ciphertext className );
}
