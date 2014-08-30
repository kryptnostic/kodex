package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public abstract class Encryptable<T> {
    protected final static ObjectMapper mapper = ( new KodexObjectMapperFactory() ).getObjectMapper();

    private final boolean encrypted;
    private final T data;
    private final String className;
    private final Ciphertext encryptedData;
    private final Ciphertext encryptedClassName;

    public Encryptable(T data) {
        this.encrypted = false;
        this.data = data;
        this.className = data.getClass().getName();
        this.encryptedData = null;
        this.encryptedClassName = null;
    }

    public Encryptable(Ciphertext ciphertext, Ciphertext className) {
        this.encrypted = true;
        this.data = null;
        this.className = null;
        this.encryptedData = ciphertext;
        this.encryptedClassName = className;
    }

    public final Encryptable<T> encrypt(Object key) throws JsonProcessingException {
        Preconditions.checkState(!encrypted);
        Preconditions.checkNotNull(data);
        Preconditions.checkNotNull(className);
        Preconditions.checkState(encryptedData == null);
        Preconditions.checkState(encryptedClassName == null);
        return encryptWith(key);
    }

    protected abstract Encryptable<T> encryptWith(Object key) throws JsonProcessingException;

    public final Encryptable<T> decrypt(Object key) throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        Preconditions.checkState(encrypted);
        Preconditions.checkState(data == null);
        Preconditions.checkState(className == null);
        Preconditions.checkNotNull(encryptedData);
        Preconditions.checkNotNull(encryptedClassName);
        return decryptWith(key);
    }

    protected abstract Encryptable<T> decryptWith(Object key) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException;

    public T getData() {
        return data;
    }
    
    public String getClassName() {
        return className;
    }

    public Ciphertext getEncryptedData() {
        return encryptedData;
    }
    
    public Ciphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    public boolean isEncrypted() {
        return encrypted;
    }
}
