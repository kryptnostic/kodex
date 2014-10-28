package com.kryptnostic.crypto.v1.ciphers;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class AesCryptoService extends AbstractCryptoService {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private byte[]              key;

    public AesCryptoService( Cypher cypher ) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        this( cypher, Cyphers.generateSecretKey( cypher ) );
    }

    public AesCryptoService( Cypher cypher, SecretKey secretKey ) {
        this( cypher, secretKey.getEncoded() );
    }

    public AesCryptoService( Cypher cypher, byte[] secretKey ) {
        super( cypher );
        this.key = secretKey;
    }

    public byte[] getSecretKey() {
        try {
            lock.readLock().lock();
            return key;
        } finally {
            lock.readLock().unlock();
        }
    }

    public BlockCiphertext encrypt( byte[] plaintext ) throws SecurityConfigurationException {
        return encrypt( plaintext, new byte[0] );
    }
    
    @Override
    protected SecretKeySpec getSecretKeySpec( byte[] salt ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            lock.readLock().lock();
            return new SecretKeySpec( key, cypher.getName() );
        } finally {
            lock.readLock().unlock();
        }
    }

    public void destroy() {
        try {
            lock.writeLock().lock();
            Arrays.fill( key, (byte) 0 );
            key = null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void reinit( byte[] newKey ) {
        try {
            lock.writeLock().lock();
            this.key = Arrays.copyOf( newKey, newKey.length );
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        Arrays.fill( key, (byte) 0 );
        super.finalize();
    }
}
