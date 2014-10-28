package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.kryptnostic.crypto.v1.ciphers.AesCryptoService;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CipherDescription;
import com.kryptnostic.crypto.v1.ciphers.CryptoAlgorithm;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.ciphers.Cyphers;
import com.kryptnostic.crypto.v1.signatures.SignatureAlgorithm;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = As.PROPERTY,
    property = Names.CLASS_FIELD )
public class Kodex<K extends Comparable<K>> implements Serializable {
    private static final long                 serialVersionUID     = 5021271922876183846L;
    private static final ObjectMapper         mapper               = KodexObjectMapperFactory.getObjectMapper();
    private static final String               SEAL_FIELD           = "sealingAlgorithm";
    private static final String               KEY_PROTECTION_FIELD = "keyProtectionAlgorithm";
    private static final String               SEALED_KEY_FIELD     = "sealedKey";
    private static final String               KEY_RING_FIELD       = "keyRing";
    private static final String               SEAL_SIGNATURE       = "sealSignature";

    private static final byte[]               empty                = new byte[ 0 ];

    private transient AesCryptoService        service              = null;
    private transient PrivateKey              privateKey;
    private transient boolean                 dirty                = false;

    private final ReadWriteLock               lock                 = new ReentrantReadWriteLock();
    private final Cypher                      seal;
    private final Cypher                      keyProtectionAlgorithm;
    private final byte[]                      encryptedKey;
    private final TreeMap<K, BlockCiphertext> keyring;
    private byte[]                            signature;

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, PublicKey publicKey ) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            SecurityConfigurationException,
            InvalidKeyException,
            SignatureException,
            JsonProcessingException {
        this( seal, keyProtectionAlgorithm, publicKey, keyProtectionAlgorithm.getKeyGenerator().generateKey()
                .getEncoded() );
    }

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, PublicKey publicKey, byte[] secretKey ) throws SecurityConfigurationException,
            InvalidKeyException,
            SignatureException,
            JsonProcessingException {
        this( seal, keyProtectionAlgorithm, Cyphers.encrypt( seal, publicKey, secretKey ) );
    }

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, byte[] encryptedKey ) throws InvalidKeyException,
            SignatureException,
            JsonProcessingException {
        this( seal, keyProtectionAlgorithm, encryptedKey, Maps.<K, BlockCiphertext> newConcurrentMap(), empty );
    }

    @JsonCreator
    public Kodex(
            @JsonProperty( SEAL_FIELD ) CipherDescription seal,
            @JsonProperty( KEY_PROTECTION_FIELD ) CipherDescription keyProtectionAlgorithm,
            @JsonProperty( SEALED_KEY_FIELD ) byte[] encryptedKey,
            @JsonProperty( KEY_RING_FIELD ) Map<K, BlockCiphertext> keyring,
            @JsonProperty( SEAL_SIGNATURE ) byte[] signature ) throws InvalidKeyException,
            SignatureException,
            JsonProcessingException {
        this(
                Cypher.createCipher( seal ),
                Cypher.createCipher( keyProtectionAlgorithm ),
                encryptedKey,
                keyring,
                signature );
    }

    public Kodex(
            Cypher seal,
            Cypher keyProtectionAlgorithm,
            byte[] encryptedKey,
            Map<K, BlockCiphertext> keyring,
            byte[] signature ) throws InvalidKeyException, SignatureException, JsonProcessingException {
        Preconditions.checkArgument(
                seal.getAlgorithm().equals( CryptoAlgorithm.RSA ),
                "Kodex only supports RSA as a public sealing and unsealing algorithm" );
        Preconditions.checkArgument(
                keyProtectionAlgorithm.getAlgorithm().equals( CryptoAlgorithm.AES ),
                "Kodex only supports AES as a ke protection" );
        this.seal = seal;
        this.keyProtectionAlgorithm = keyProtectionAlgorithm;
        this.encryptedKey = encryptedKey;
        this.keyring = Maps.newTreeMap();
        this.keyring.putAll( keyring );
        this.signature = signature;
        seal();
    }

    @JsonProperty( SEAL_FIELD )
    public Cypher getSeal() {
        return seal;
    }

    @JsonProperty( KEY_PROTECTION_FIELD )
    public Cypher getKeyProtectionAlgorithm() {
        return keyProtectionAlgorithm;
    }

    @JsonProperty( SEALED_KEY_FIELD )
    public byte[] getEncryptedKey() {
        return encryptedKey;
    }

    @JsonProperty( KEY_RING_FIELD )
    public Map<K, BlockCiphertext> getKeyring() {
        return keyring;
    }

    @JsonProperty( SEAL_SIGNATURE )
    public byte[] getSignature() throws InvalidKeyException, SignatureException, JsonProcessingException {
        updateSignature();
        return signature;
    }

    public void updateSignature() throws InvalidKeyException, SignatureException, JsonProcessingException {
        if ( dirty && ( privateKey != null ) ) {
            signature = Keys.sign( privateKey, SignatureAlgorithm.SHA512withRSA, signatureData() );
            dirty = false;
        }
    }

    public byte[][] signatureData() throws JsonProcessingException {
        return new byte[][] { mapper.writeValueAsBytes( this.seal ),
                mapper.writeValueAsBytes( keyProtectionAlgorithm ), mapper.writeValueAsBytes( this.encryptedKey ),
                mapper.writeValueAsBytes( this.keyring ) };
    }

    public void unseal( PrivateKey privateKey ) throws KodexException, SecurityConfigurationException,
            CorruptKodexException {
        try {
            lock.writeLock().lock();
            verify( Keys.publicKeyFromPrivateKey( privateKey ) );

            service = new AesCryptoService( keyProtectionAlgorithm, Cyphers.decrypt( seal, privateKey, encryptedKey ) );
            this.privateKey = privateKey;
        } catch ( InvalidKeyException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( NoSuchAlgorithmException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( JsonProcessingException e ) {
            throw new KodexException( e );
        } catch ( InvalidKeySpecException e ) {
            throw new SecurityConfigurationException( e );
        } catch ( SignatureException e ) {
            throw new SecurityConfigurationException( e );
        } finally {
            lock.writeLock().unlock();
        }
    }

    public <T> void setKey( K key, KodexMarshaller<T> marshaller, T object ) throws SecurityConfigurationException,
            SealedKodexException, KodexException {
        checkAndThrowIfSealed();
        try {
            for ( K existing : keyring.keySet() ) {
                if ( !existing.equals( key ) && existing.compareTo( key ) == 0 ) {
                    throw new KodexException( "Keys cannot compare to zero, when they are not equal." );
                }
            }
            keyring.put( key, service.encrypt( marshaller.toBytes( object ), empty ) );
            dirty = true;
        } catch ( IOException e ) {
            throw new KodexException( e );
        }
    }

    public <T> T getKey( K key, KodexMarshaller<T> marshaller ) throws SealedKodexException,
            SecurityConfigurationException, KodexException {
        checkAndThrowIfSealed();
        byte[] rawBytes = null;
        try {
            lock.readLock().lock();
            BlockCiphertext value = keyring.get( key );
            if ( value == null ) {
                return null;
            }
            rawBytes = service.decryptBytes( value );

        } finally {
            lock.readLock().unlock();
        }

        try {
            return marshaller.fromBytes( rawBytes );
        } catch ( IOException e ) {
            throw new KodexException( e );
        }
    }

    public <T> T getKeyWithJackson( K key, Class<T> clazz ) throws SealedKodexException,
            SecurityConfigurationException, KodexException {
        return getKey( key, new JacksonKodexMarshaller<T>( clazz ) );
    }

    @SuppressWarnings( "unchecked" )
    public <T> T getKeyWithJackson( Class<T> clazz ) throws KodexException, SecurityConfigurationException {
        try {
            return getKey( (K) clazz.getCanonicalName(), new JacksonKodexMarshaller<T>( clazz ) );

        } catch ( SealedKodexException e ) {
            throw new KodexException( e );
        }
    }

    public <T> T getKeyWithJackson( K key, TypeReference<T> reference ) throws SealedKodexException,
            SecurityConfigurationException, KodexException {

        return getKey( key, new JacksonTypeRefKodexMarshaller<T>( reference ) );

    }

    public <T> void setKeyWithJackson( K key, T object, Class<T> clazz ) throws KodexException,
            SecurityConfigurationException {
        try {
            setKey( key, new JacksonKodexMarshaller<T>( clazz ), object );
        } catch ( SealedKodexException e ) {
            throw new KodexException( e );
        }
    }

    @SuppressWarnings( "unchecked" )
    public <T> void setKeyWithClassAndJackson( Class<T> clazz, T object ) throws SealedKodexException, KodexException,
            SecurityConfigurationException {
        setKey( (K) clazz.getCanonicalName(), new JacksonKodexMarshaller<T>( clazz ), object );
    }

    public <T> void setKeyWithJackson( K key, T object, TypeReference<T> reference ) throws SealedKodexException,
            KodexException, SecurityConfigurationException {
        setKey( key, new JacksonTypeRefKodexMarshaller<T>( reference ), object );
    }

    public void verify( PublicKey publicKey ) throws InvalidKeyException, SignatureException, CorruptKodexException,
            JsonProcessingException {
        if ( privateKey != null ) {
            updateSignature();
        }
        if ( signature.length != 0 ) {
            if ( !Keys.verify( publicKey, SignatureAlgorithm.SHA512withRSA, signature, signatureData() ) ) {
                throw new CorruptKodexException( "Kodex signature validation failed" );
            }
        }
    }

    public boolean containsKey( K key ) throws SealedKodexException {
        checkAndThrowIfSealed();
        boolean contained = false;
        try {
            lock.readLock().lock();
            contained = keyring.containsKey( key );
        } finally {
            lock.readLock().unlock();
        }
        return contained;
    }

    public void seal() throws InvalidKeyException, SignatureException, JsonProcessingException {
        if ( isSealed() ) {
            return;
        }
        try {
            lock.writeLock().lock();
            if ( privateKey != null ) {
                signature = getSignature();
            } else {
                throw new SignatureException( "Kodex requires a private key to be sealed" );
            }
            service.destroy();
            service = null;
            privateKey = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @JsonIgnore
    public boolean isSealed() {
        return service == null;
    }

    @JsonIgnore
    public boolean isDirty() {
        return dirty;
    }

    public void checkAndThrowIfSealed() throws SealedKodexException {
        if ( isSealed() ) {
            throw new SealedKodexException( "Kodex is currently sealed!" );
        }
    }

    public static class SealedKodexException extends Exception {
        private static final long serialVersionUID = -1688906592450708294L;

        public SealedKodexException( String message ) {
            super( message );
        }
    }

    public static class CorruptKodexException extends Exception {
        private static final long serialVersionUID = -1688906592450708294L;

        public CorruptKodexException( String message ) {
            super( message );
        }

        public CorruptKodexException( Throwable e ) {
            super( e );
        }
    }

}
