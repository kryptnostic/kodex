package com.kryptnostic.kodex.v1.models;

import java.security.PublicKey;

import com.kryptnostic.crypto.Ciphertext;

public class AesEncryptable<T> extends Encryptable<T> {

    public AesEncryptable(T data) {
        super(data);
    }

    public AesEncryptable(Ciphertext encryptedData, Ciphertext encryptedClassName) {
        super(encryptedData, encryptedClassName);
    }

    @Override
    public Encryptable<T> encryptWith(Object key) {
        if (key instanceof PublicKey) {
            return aesEncrypt((PublicKey) key);
        }
        throw new IllegalArgumentException("Key type not supported");
    }

    private Encryptable<T> aesEncrypt(PublicKey key) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Encryptable<T> decryptWith(Object key) {
        throw new UnsupportedOperationException();
    }

}
