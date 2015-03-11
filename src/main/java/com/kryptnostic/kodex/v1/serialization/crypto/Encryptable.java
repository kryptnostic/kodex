package com.kryptnostic.kodex.v1.serialization.crypto;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.binary.StringUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.storage.v1.models.EncryptableBlock;

/**
 * An Encryptable object wraps any Java object and allows for deferred encryption of the bytes of that object
 * <p>
 * This implementation uses Jackson's ObjectMapper to serialize the bytes of the data object
 * <p>
 * <h2>Two States</h2>
 * <p>
 * An Encryptable is immutable and can only be instantiated in one of two states: encrypted or decrypted
 * <p>
 * Encryption and decryption are not destructive operations and do not change the state of the Encryptable the operation
 * is called on. Encryption/decryption will simply generate and return new Encryptables without modifying and state
 * within the original Encryptable. Specifically, when an Encryptable is encrypted from a decrypted state, a new
 * Encryptable (in the encrypted state) is generated and returned. Similarly, when an Encryptable is decrypted from an
 * encrypted state, a new Encryptable (in the decrypted state) is generated and returned. The exception to this is if
 * one tries to encrypt an Encryptable that is already in an Encrypted state, the original Encryptable will be returned
 * (no-op). Similarly, if one tries to decrypt an Encryptable that is already in a decrypted state, the original
 * Encryptable will be returned (no-op).
 * <p>
 * This is useful for deferring potentially expensive encryption and serialization operations while still being able to
 * pass data around in memory
 * <p>
 * <h2>Automatic Decryption/Encryption</h2>
 * <p>
 * Encryptable supports automatic decryption and encryption when a respective deserialization or serialization operation
 * occurs. This is accomplished by registering the Encryptable with a CryptoServiceId. The CryptoServiceId is a string
 * key that maps to a CryptoService. A CryptoService stores keys for performing encryption/decryption operations. By
 * registering a @JacksonInjectable CryptoServiceLoader with Jackson's ObjectMapper (@see KodexModule) we are able to
 * accomplish seamless encryption/decryption upon serialization/deserialization with Jackson
 * <p>
 * If a configured Jackson ObjectMapper attempts to deserialize/decrypt an Encryptable, but doesn't have an appropriate
 * CryptoService to do so, Jackson will return a deserialized Encryptable in an ENCRYPTED state. This means that
 * decryption only occurs when the security configuration is properly registered, and if not, decryption will fail
 * silently.
 * <p>
 * <h2>Blocks</h2>
 * <p>
 * Data bytes are automatically divided into blocks for efficient server-side storage and more reliable transmission.
 * This subdivision automatically occurs upon encryption. The process is reversed upon decryption to provide a java
 * object representation of the plaintext data
 * <p>
 * <h2>Notes</h2>
 * <p>
 * Equals is not overridden because the encrypted bytes are random and comparing two Encryptables while in an encrypted
 * state may consistently return false although their underlying plaintext data may be equal
 * 
 * @author sinaiman
 *
 * @param <T> The type of the wrapped data you want to encrypt
 */
public class Encryptable<T> implements Serializable {
    private static final long          serialVersionUID = 5128167833341065251L;
    /**
     * This hash function is used to validate block integrity
     */
    public static final HashFunction   hashFunction     = Hashing.sha256();
    protected static ObjectMapper      mapper           = KodexObjectMapperFactory.getObjectMapper();

    @JsonIgnore
    private final boolean              encrypted;
    @JsonIgnore
    private transient final T          data;
    @JsonIgnore
    private transient final String     className;
    @JsonProperty( Names.DATA_FIELD )
    protected final EncryptableBlock[] encryptedData;
    @JsonProperty( Names.NAME_FIELD )
    protected final BlockCiphertext    encryptedClassName;
    @JsonProperty( Names.KEY_FIELD )
    protected final String             cryptoServiceId;
    @JsonIgnore
    protected transient ByteBuffer     plaintext;
    @JsonProperty( Names.STRATEGY_FIELD )
    protected ChunkingStrategy         chunkingStrategy;

    /**
     * @param data A plaintext java object representation of data that will later be encrypted if serialized
     * @param cryptoServiceId A string key that maps this object to its appropriate CryptoService for
     *            decryption/encryption operations
     */
    public Encryptable( T data, String cryptoServiceId ) {
        this( data, cryptoServiceId, new DefaultChunkingStrategy() );
    }

    protected Encryptable( T data, String cryptoServiceId, ChunkingStrategy chunkingStrategy ) {
        this.encrypted = false;
        this.data = data;
        this.className = data.getClass().getName();
        this.encryptedData = null;
        this.encryptedClassName = null;
        this.cryptoServiceId = cryptoServiceId;
        this.chunkingStrategy = chunkingStrategy;
    }

    /**
     * @param data A plaintext java object representation of data that will later be encrypted if serialized
     */
    public Encryptable( T data ) {
        this( data, PasswordCryptoService.class.getCanonicalName(), new DefaultChunkingStrategy() );
    }

    protected Encryptable( EncryptableBlock[] ciphertext, BlockCiphertext className, ChunkingStrategy chunkingStrategy ) throws ClassNotFoundException,
            SecurityConfigurationException,
            IOException {
        this( ciphertext, className, PasswordCryptoService.class.getCanonicalName(), chunkingStrategy );
    }

    /**
     * @param ciphertext An array of encrypted byte[] representations of data
     * @param className An encrypted string representation of the data's target java class
     * @throws SecurityConfigurationException If any crypto operations fail
     * @throws IOException If block split fails
     * @throws ClassNotFoundException If target class doesn't exist on local JVM
     */
    public Encryptable( EncryptableBlock[] ciphertext, BlockCiphertext className ) throws IOException,
            ClassNotFoundException,
            SecurityConfigurationException {
        this( ciphertext, className, new DefaultChunkingStrategy() );
    }

    protected Encryptable(
            EncryptableBlock[] ciphertext,
            BlockCiphertext className,
            String cryptoServiceId,
            ChunkingStrategy chunkingStrategy ) throws ClassNotFoundException,
            SecurityConfigurationException,
            IOException {
        this( ciphertext, className, cryptoServiceId, null, chunkingStrategy );
    }

    /**
     * @param ciphertext An array of encrypted byte[] representations of data
     * @param className An encrypted string representation of the data's target java class
     * @param cryptoServiceId A string key that maps this object to its appropriate CryptoService for
     *            decryption/encryption operations
     * @throws SecurityConfigurationException If any crypto operations fail
     * @throws IOException If block split fails
     * @throws ClassNotFoundException If target class doesn't exist on local JVM
     */
    public Encryptable( EncryptableBlock[] ciphertext, BlockCiphertext className, String cryptoServiceId ) throws ClassNotFoundException,
            SecurityConfigurationException,
            IOException {
        this( ciphertext, className, cryptoServiceId, null, new DefaultChunkingStrategy() );
    }

    /**
     * @param ciphertext An array of encrypted byte[] representations of data
     * @param className An encrypted string representation of the data's target java class
     * @param cryptoServiceId A string key that maps this object to its appropriate CryptoService for
     *            decryption/encryption operations
     * @param loader A CryptoServiceLaoder is a key/value store for String=CryptoService
     * @param chunkingStrategy Strategy for splitting and joining blocks
     * @throws SecurityConfigurationException If any crypto operations fail
     * @throws IOException If block split fails
     * @throws ClassNotFoundException If target class doesn't exist on local JVM
     */
    public Encryptable(
            EncryptableBlock[] ciphertext,
            BlockCiphertext className,
            String cryptoServiceId,
            CryptoServiceLoader loader,
            ChunkingStrategy chunkingStrategy ) throws SecurityConfigurationException,
            ClassNotFoundException,
            IOException {
        this.cryptoServiceId = cryptoServiceId;
        this.chunkingStrategy = chunkingStrategy;

        CryptoService crypto = null;

        try {
            crypto = getCryptoService( loader );
        } catch ( SecurityConfigurationException e ) {}

        if ( crypto != null ) {
            Encryptable<T> encrypted = new Encryptable<T>( ciphertext, className, cryptoServiceId );
            Encryptable<T> decrypted;
            decrypted = encrypted.decryptWith( crypto );
            this.encrypted = false;
            this.data = decrypted.getData();
            this.className = decrypted.getClassName();
            this.encryptedData = null;
            this.encryptedClassName = null;
        } else {
            this.encrypted = true;
            this.data = null;
            this.className = null;
            this.encryptedData = ciphertext;
            this.encryptedClassName = className;
        }
    }

    /**
     * @param ciphertext An array of encrypted byte[] representations of data
     * @param className An encrypted string representation of the data's target java class
     * @param cryptoServiceId A string key that maps this object to its appropriate CryptoService for
     *            decryption/encryption operations
     * @param loader A CryptoServiceLaoder is a key/value store for String=CryptoService
     * @throws SecurityConfigurationException If any crypto operations fail
     * @throws IOException If block split fails
     * @throws ClassNotFoundException If target class doesn't exist on local JVM
     */
    @JsonCreator
    public Encryptable(
            @JsonProperty( Names.DATA_FIELD ) EncryptableBlock[] ciphertext,
            @JsonProperty( Names.NAME_FIELD ) BlockCiphertext className,
            @JsonProperty( Names.KEY_FIELD ) String cryptoServiceId,
            @JacksonInject CryptoServiceLoader loader ) throws SecurityConfigurationException,
            ClassNotFoundException,
            IOException {
        this( ciphertext, className, cryptoServiceId, loader, new DefaultChunkingStrategy() );
    }

    /**
     * @param loader Key/value store that provides CryptoServices
     * @return An Encryptable in an encrypted state
     * @throws SecurityConfigurationException If something goes wrong with encryption
     * @throws IOException If block split fails
     * @throws ClassNotFoundException
     */
    public final Encryptable<T> encrypt( CryptoServiceLoader loader ) throws SecurityConfigurationException,
            IOException, ClassNotFoundException {
        if ( this.encrypted ) {
            return this;
        }

        Preconditions.checkNotNull( this.data );
        Preconditions.checkNotNull( this.className );
        Preconditions.checkState( this.encryptedData == null );
        Preconditions.checkState( this.encryptedClassName == null );

        CryptoService crypto = getCryptoService( loader );

        return encryptWith( crypto );
    }

    protected Encryptable<T> encryptWith( CryptoService crypto ) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        Preconditions.checkNotNull( crypto );

        int total = getBlockCount();
        List<BlockCiphertext> ciphertextBlocks = Lists.newArrayListWithCapacity( total );

        // encrypt all our plaintext blocks
        Iterable<byte[]> plainBlocks = getChunkingStrategy().split( getData() );
        for ( byte[] block : plainBlocks ) {
            ciphertextBlocks.add( crypto.encrypt( block ) );
        }

        Preconditions.checkState( ciphertextBlocks.size() == total, "Block count doesn't match iterable length" );

        EncryptableBlock[] blocks = new EncryptableBlock[ total ];

        BlockCiphertext encryptedClassName = crypto.encrypt( StringUtils.getBytesUtf8( getClassName() ) );

        for ( int i = 0; i < total; ++i ) {
            BlockCiphertext ciphertext = ciphertextBlocks.get( i );
            boolean isLast = i == total - 1;
            blocks[ i ] = new EncryptableBlock( ciphertext, hashFunction.hashBytes( ciphertext.getContents() )
                    .asBytes(), i, isLast, encryptedClassName, getChunkingStrategy(), DateTime.now() );
        }

        return new Encryptable<T>( blocks, encryptedClassName, cryptoServiceId );
    }

    /**
     * @param loader Key/value store that provides CryptoServices
     * @return If the current object is encrypted, returns new Encryptable equivalent to the current object, but in a
     *         decrypted state. If the current object is decrypted, returns itself
     * @throws SecurityConfigurationException If anything goes wrong with decrypting the Encryptable
     * @throws IOException If there was an error with block joining
     * @throws ClassNotFoundException If the target data object is not found in the local JVM
     */
    public final Encryptable<T> decrypt( CryptoServiceLoader loader ) throws SecurityConfigurationException,
            ClassNotFoundException, IOException {
        if ( !this.encrypted ) {
            return this;
        }

        Preconditions.checkState( this.data == null );
        Preconditions.checkState( this.className == null );
        Preconditions.checkNotNull( this.encryptedData );
        Preconditions.checkNotNull( this.encryptedClassName );

        CryptoService crypto = getCryptoService( loader );

        return decryptWith( crypto );
    }

    @SuppressWarnings( "unchecked" )
    protected Encryptable<T> decryptWith( CryptoService crypto ) throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        byte[] bytes = crypto.decryptBytes( getEncryptedClassName() );
        String className = StringUtils.newStringUtf8( bytes );
        Class<T> klass = (Class<T>) Class.forName( className );
        // Lazy evaluated transformation to re-construct object.
        T joinedData = getChunkingStrategy().join(
                Iterables.transform( Arrays.asList( getEncryptedData() ), new BlockDecrypter( crypto ) ),
                klass );
        return new Encryptable<T>( joinedData, cryptoServiceId );
    }

    /**
     * @return Number of blocks this Encryptable is using to store plaintext bytes
     * @throws JsonProcessingException If anything goes wrong writing bytes to figure out how many blocks there are TODO
     *             this method is undefined if the object is in an encrypted state, add safeguards for this case
     */
    public int getBlockCount() throws JsonProcessingException {
        if ( encrypted ) {
            return encryptedData.length;
        } else {
            if ( plaintext == null ) {
                byte[] bytes = null;
                if ( getData() instanceof String ) {
                    bytes = StringUtils.getBytesUtf8( (String) getData() );
                } else {
                    bytes = mapper.writeValueAsBytes( getData() );
                }
                plaintext = ByteBuffer.wrap( bytes );
            }
            double remaining = plaintext.remaining();
            double blockLen = getChunkingStrategy().getLength();
            return (int) Math.ceil( remaining / blockLen );
        }
    }

    protected CryptoService getCryptoService( CryptoServiceLoader loader ) throws SecurityConfigurationException {
        if ( loader == null ) {
            throw new SecurityConfigurationException( "No CryptoServiceLoader was found" );
        }

        try {
            return loader.get( cryptoServiceId );
        } catch ( NullPointerException | ExecutionException e ) {
            wrapSecurityConfigurationException( e );
        }
        wrapSecurityConfigurationException( new Exception( "Invalid loader" ) );
        return null;
    }

    private void wrapSecurityConfigurationException( Exception e ) throws SecurityConfigurationException {
        throw new SecurityConfigurationException( "Error occurred while trying to encrypt or decrypt data.", e );
    }

    /**
     * @return Plaintext data representation of object type T
     */
    @JsonIgnore
    public T getData() {
        return data;
    }

    /**
     * @return Plaintext representation of the target class name. If the Encryptable is in an encrypted state, this
     *         returns null
     */
    @JsonIgnore
    public String getClassName() {
        return className;
    }

    /**
     * @return Encrypted representation of data payload. If the Encryptable is in a decrypted state, this returns null
     */
    @JsonProperty( Names.DATA_FIELD )
    public EncryptableBlock[] getEncryptedData() {
        return encryptedData;
    }

    /**
     * 
     * @return Encrypted representation of target class for data payload. If the Encryptable is in a decrypted state,
     *         this returns null
     */
    @JsonProperty( Names.NAME_FIELD )
    public BlockCiphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    /**
     * @return True if the Encryptable is in an encrypted state, false if it is in a decrypted state
     */
    @JsonIgnore
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * @return The key corresponding to this Encryptable's CryptoService to be used to load a CryptoService from an
     *         appropriate CryptoServiceLoader
     */
    @JsonProperty( Names.KEY_FIELD )
    public String getCryptoServiceId() {
        return cryptoServiceId;
    }

    @JsonProperty( Names.STRATEGY_FIELD )
    public ChunkingStrategy getChunkingStrategy() {
        return chunkingStrategy;
    }

    /**
     * @return An ObjectMapper that is compatible with Encryptables
     */
    @JsonIgnore
    public static ObjectMapper getMapper() {
        return mapper;
    }

    // TODO FIXME wtf code smell
    public static void setMapper( ObjectMapper mapper ) {
        // Encryptable.mapper = mapper;
    }

}
