package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class Encryptable<T> {
    private final static ObjectMapper mapper = ( new KodexObjectMapperFactory() ).getObjectMapper(null);

    private static final String MSG_UNRECOGNIZED = "Encryption scheme not recognized";
    private static final String MSG_INVALID_KEY = "Encryption scheme recognized, but provided type of key is not supported";

    public static final String FIELD_SCHEME = "s";
    public static final String FIELD_ENCRYPTED_CLASS_NAME = "c";
    public static final String FIELD_ENCRYPTED_DATA = "d";

    @JsonIgnore
    private final boolean encrypted;
    @JsonIgnore
    private final T data;
    @JsonIgnore
    private final String className;
    @JsonProperty(FIELD_ENCRYPTED_DATA)
    private final Ciphertext encryptedData;
    @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME)
    private final Ciphertext encryptedClassName;
    @JsonProperty(FIELD_SCHEME)
    private final EncryptionScheme scheme;

    public enum EncryptionScheme {
        AES, FHE
    };

    public Encryptable(T data, EncryptionScheme scheme) {
        this.encrypted = false;
        this.data = data;
        this.className = data.getClass().getName();
        this.encryptedData = null;
        this.encryptedClassName = null;
        this.scheme = scheme;
    }

    @JsonCreator
    public Encryptable(@JsonProperty(FIELD_ENCRYPTED_DATA) Ciphertext ciphertext,
            @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME) Ciphertext className,
            @JsonProperty(FIELD_SCHEME) EncryptionScheme scheme) {
        this.encrypted = true;
        this.data = null;
        this.className = null;
        this.encryptedData = ciphertext;
        this.encryptedClassName = className;
        this.scheme = scheme;
    }

    public final Encryptable<T> encrypt(Object key) throws JsonProcessingException {
        if (this.encrypted) {
            return this;
        }
        Preconditions.checkNotNull(this.data);
        Preconditions.checkNotNull(this.className);
        Preconditions.checkState(this.encryptedData == null);
        Preconditions.checkState(this.encryptedClassName == null);
        Preconditions.checkState(this.scheme != null);

        switch (this.scheme) {
            case FHE:
                if (!( key instanceof PublicKey )) {
                    throw new UnsupportedOperationException(MSG_INVALID_KEY);
                }
                return encryptWithFhe((PublicKey) key);
        }

        throw new UnsupportedOperationException(MSG_UNRECOGNIZED);
    }

    private Encryptable<T> encryptWithFhe(PublicKey key) throws JsonProcessingException {
        Ciphertext encryptedData = key.encryptIntoEnvelope(mapper.writeValueAsBytes(getData()));
        Ciphertext encryptedClassName = key.encryptIntoEnvelope(getClassName().getBytes());
        return new Encryptable<T>(encryptedData, encryptedClassName, EncryptionScheme.FHE);
    }

    public final Encryptable<T> decrypt(Object key) throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        if (!this.encrypted) {
            return this;
        }
        Preconditions.checkState(this.data == null);
        Preconditions.checkState(this.className == null);
        Preconditions.checkNotNull(this.encryptedData);
        Preconditions.checkNotNull(this.encryptedClassName);
        Preconditions.checkState(this.scheme != null);

        switch (scheme) {
            case FHE:
                if (!( key instanceof PrivateKey )) {
                    throw new UnsupportedOperationException(MSG_INVALID_KEY);
                }
                return decryptWithFhe((PrivateKey) key);
        }

        throw new UnsupportedOperationException(MSG_UNRECOGNIZED);
    }

    protected Encryptable<T> decryptWithFhe(PrivateKey key) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException {
        String className = StringUtils.newStringUtf8(key.decryptFromEnvelope(getEncryptedClassName()));
        byte[] objectBytes = key.decryptFromEnvelope(getEncryptedData());
        @SuppressWarnings("unchecked")
        T obj = mapper.<T> readValue(objectBytes, (Class<T>) Class.forName(className));
        return new Encryptable<T>(obj, this.scheme);
    }

    @JsonIgnore
    public T getData() {
        return data;
    }

    @JsonIgnore
    public String getClassName() {
        return className;
    }

    @JsonProperty(FIELD_ENCRYPTED_DATA)
    public Ciphertext getEncryptedData() {
        return encryptedData;
    }

    @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME)
    public Ciphertext getEncryptedClassName() {
        return encryptedClassName;
    }

    @JsonProperty(FIELD_SCHEME)
    public EncryptionScheme getScheme() {
        return scheme;
    }

    @JsonIgnore
    public boolean isEncrypted() {
        return encrypted;
    }
}
