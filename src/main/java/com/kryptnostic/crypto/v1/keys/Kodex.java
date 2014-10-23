package com.kryptnostic.crypto.v1.keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.kryptnostic.crypto.v1.ciphers.AesCryptoService;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.ciphers.Cyphers;

public class Kodex {
    private static final byte[] empty = new byte[0];
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private transient AesCryptoService service;
    private Cypher seal; 
    private byte[] encryptedKey;
    private Map<String,BlockCiphertext> keyring;
    
    public void unseal( PrivateKey privateKey ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        try {
            lock.writeLock().lock();
            service = new AesCryptoService(Cypher.AES_CTR_PKCS5_128,Cyphers.decrypt( seal , privateKey, encryptedKey) );
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public <T> void setKey( KodexFactory<T> factory, T object ) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, SealedKodexException {
        checkAndThrowIfSealed();
        keyring.put( factory.getName( object ) , service.encrypt( factory.toBytes( object ) , empty ) );
    }
    
    public <T> T getKey( KodexFactory<T> factory, Class<T> clazz ) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, SealedKodexException {
        checkAndThrowIfSealed();
        byte[] rawBytes = null;
        try {
            lock.readLock().lock();
            rawBytes = service.decryptBytes( keyring.get( clazz.getName() ) );
        } finally {
            lock.readLock().unlock();
        }
        
        return factory.fromBytes( rawBytes );
    }
    
    public void seal() {
        if( isSealed() ) {
            return;
        }
        try{ 
           lock.writeLock().lock();
           service.destroy();
           service = null;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public boolean isSealed() {
        return service == null;
    }
    
    public void checkAndThrowIfSealed() throws SealedKodexException {
        if( isSealed() ) {
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
