package com.kryptnostic.crypto.v1.keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.tuple.Pair;

import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;

public class Kodex {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private transient byte[] iv;
    private transient byte[] key;
    private transient CryptoService service;
    
    Map<String,BlockCiphertext> keyring;
    
    public void unseal( char[] password ) {
        try {
            lock.writeLock().lock();
            this.iv = Arrays.copyOf( iv , iv.length );
            this.key = Arrays.copyOf( key , key.length );
            service = new CryptoService(Cypher.AES_CTR_128, password);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public <T> void setKey( KodexFactory<T> factory, T object ) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, SealedKodexException {
        checkAndThrowIfSealed();
        keyring.put( factory.getName( object ) , service.encrypt( factory.toBytes( object ) ) );
    }
    
    public <T> T getKey( KodexFactory<T> factory, Class<T> clazz ) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, SealedKodexException {
        checkAndThrowIfSealed();
        Pair<Integer,byte[]> rawBytes = null;
        try {
            lock.readLock().lock();
            rawBytes = service.rawDecrypt( keyring.get( clazz.getName() ) );
            assert rawBytes.getRight().length == rawBytes.getLeft();
        } finally {
            lock.readLock().unlock();
        }
        
        return factory.fromBytes( rawBytes.getRight() );
    }
    
    public void seal() {
        if( isSealed() ) {
            return;
        }
        try{ 
           lock.writeLock().lock();
           Arrays.fill( iv , (byte) 0 );
           Arrays.fill( key , (byte) 0 );
           iv = null;
           key = null;
           service = null;
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public boolean isSealed() {
        return (iv==null)||(key==null);
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
