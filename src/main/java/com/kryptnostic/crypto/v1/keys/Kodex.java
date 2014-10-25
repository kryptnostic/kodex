package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.kryptnostic.crypto.v1.ciphers.AesCryptoService;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CipherDescription;
import com.kryptnostic.crypto.v1.ciphers.CryptoAlgorithm;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.ciphers.Cyphers;
import com.kryptnostic.kodex.v1.constants.Names;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = As.PROPERTY,
    property = Names.CLASS_FIELD )
public class Kodex<K> implements Serializable {
    private static final long             serialVersionUID     = 5021271922876183846L;
    private static final String           SEAL_FIELD           = "sealingAlgorithm";
    private static final String           KEY_PROTECTION_FIELD = "keyProtectionAlgorithm";
    private static final String           SEALED_KEY_FIELD     = "sealedKey";
    private static final String           KEY_RING_FIELD       = "keyRing";

    private static final byte[]           empty                = new byte[ 0 ];
    private transient AesCryptoService    service              = null;

    private final ReadWriteLock           lock                 = new ReentrantReadWriteLock();
    private final Cypher                  seal;
    private final Cypher                  keyProtectionAlgorithm;
    private final byte[]                  encryptedKey;
    private final Map<K, BlockCiphertext> keyring;

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, PublicKey publicKey ) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchPaddingException {
        this( seal, keyProtectionAlgorithm, publicKey, keyProtectionAlgorithm.getKeyGenerator().generateKey()
                .getEncoded() );
    }

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, PublicKey publicKey, byte[] secretKey ) throws InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        this( seal, keyProtectionAlgorithm, Cyphers.encrypt( seal, publicKey, secretKey ) );
    }

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, byte[] encryptedKey ) {
        this( seal, keyProtectionAlgorithm, encryptedKey, Maps.<K, BlockCiphertext> newConcurrentMap() );
    }

    @JsonCreator
    public Kodex(
            @JsonProperty( SEAL_FIELD ) CipherDescription seal,
            @JsonProperty( KEY_PROTECTION_FIELD ) CipherDescription keyProtectionAlgorithm,
            @JsonProperty( SEALED_KEY_FIELD ) byte[] encryptedKey,
            @JsonProperty( KEY_RING_FIELD ) Map<K, BlockCiphertext> keyring ) {
        this( Cypher.createCipher( seal ), Cypher.createCipher( keyProtectionAlgorithm ), encryptedKey, keyring );
    }

    public Kodex( Cypher seal, Cypher keyProtectionAlgorithm, byte[] encryptedKey, Map<K, BlockCiphertext> keyring ) {
        Preconditions.checkArgument(
                seal.getAlgorithm().equals( CryptoAlgorithm.RSA ),
                "Kodex only supports RSA as a public sealing and unsealing algorithm" );
        this.seal = seal;
        this.keyProtectionAlgorithm = keyProtectionAlgorithm;
        this.encryptedKey = encryptedKey;
        this.keyring = Maps.newConcurrentMap();
        this.keyring.putAll( keyring );
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

    public void unseal( PrivateKey privateKey ) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            lock.writeLock().lock();
            service = new AesCryptoService( keyProtectionAlgorithm, Cyphers.decrypt( seal, privateKey, encryptedKey ) );
        } finally {
            lock.writeLock().unlock();
        }
    }

    public <T> void setKey( K key, KodexMarshaller<T> marshaller, T object ) throws InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidParameterSpecException, SealedKodexException, IOException {
        checkAndThrowIfSealed();
        keyring.put( key, service.encrypt( marshaller.toBytes( object ), empty ) );
    }

    public <T> T getKey( K key, KodexMarshaller<T> marshaller ) throws InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, SealedKodexException, IOException {
        checkAndThrowIfSealed();
        byte[] rawBytes = null;
        try {
            lock.readLock().lock();
            rawBytes = service.decryptBytes( keyring.get( key ) );
        } finally {
            lock.readLock().unlock();
        }

        return marshaller.fromBytes( rawBytes );
    }

    public <T> T getKeyWithJackson( K key, Class<T> clazz ) throws InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, SealedKodexException, IOException {
        return getKey( key, new JacksonKodexMarshaller<T>( clazz ) );
    }

    public <T> T getKeyWithJackson( K key, TypeReference<T> reference ) throws InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, SealedKodexException, IOException {
        return getKey( key, new JacksonTypeRefKodexMarshaller<T>( reference ) );
    }

    public <T> void setKeyWithJackson( K key, T object, Class<T> clazz ) throws InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidParameterSpecException, SealedKodexException, IOException {
        setKey( key, new JacksonKodexMarshaller<T>( clazz ), object );
    }

    public <T> void setKeyWithJackson( K key, T object, TypeReference<T> reference ) throws InvalidKeyException,
            InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidParameterSpecException, SealedKodexException, IOException {
        setKey( key, new JacksonTypeRefKodexMarshaller<T>( reference ), object );
    }

    public boolean containsKey( K key ) throws SealedKodexException {
        checkAndThrowIfSealed();
        lock.readLock().lock();
        boolean contained = keyring.containsKey( key );
        lock.readLock().unlock();
        return contained;
    }

    public void seal() {
        if ( isSealed() ) {
            return;
        }
        try {
            lock.writeLock().lock();
            service.destroy();
            service = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @JsonIgnore
    public boolean isSealed() {
        return service == null;
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
}
