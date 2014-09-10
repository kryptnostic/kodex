package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Preconditions;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.security.SecurityConfiguration;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = Encryptable.FIELD_CLASS)
public abstract class Encryptable<T> {

    public static final String FIELD_CLASS = "@class";
    public static final String FIELD_ENCRYPTED_CLASS_NAME = "name";
    public static final String FIELD_ENCRYPTED_DATA = "data";

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

    public Encryptable(Ciphertext ciphertext, Ciphertext className, SecurityConfigurationMapping mapping)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        if (canDecryptWith(mapping)) {
            Encryptable<T> encrypted = createEncrypted(ciphertext, className);
            Encryptable<T> decrypted = encrypted.decryptWith(mapping);
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

    protected abstract Encryptable<T> createEncrypted(Ciphertext ciphertext, Ciphertext className);

    protected boolean canDecryptWith(SecurityConfigurationMapping mapping) {
        SecurityConfiguration config = null;
        if (mapping != null) {
            config = mapping.get(this.getClass());
            return config.getPrivateKey() != null;
        }
        return false;
    }

    public final Encryptable<T> encrypt(SecurityConfigurationMapping service) throws JsonProcessingException {
        if (this.encrypted) {
            return this;
        }
        Preconditions.checkNotNull(this.data);
        Preconditions.checkNotNull(this.className);
        Preconditions.checkState(this.encryptedData == null);
        Preconditions.checkState(this.encryptedClassName == null);

        checkServiceMapping(service);

        return encryptWith(service);
    }

    protected abstract Encryptable<T> encryptWith(SecurityConfigurationMapping service) throws JsonProcessingException;

    public final Encryptable<T> decrypt(SecurityConfigurationMapping service) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException {
        if (!this.encrypted) {
            return this;
        }
        Preconditions.checkState(this.data == null);
        Preconditions.checkState(this.className == null);
        Preconditions.checkNotNull(this.encryptedData);
        Preconditions.checkNotNull(this.encryptedClassName);

        checkServiceMapping(service);

        return decryptWith(service);
    }

    private void checkServiceMapping(SecurityConfigurationMapping service) {
        if (service == null) {
            throw new NullPointerException("Security configuration mapping not defined for "
                    + this.getClass().getCanonicalName());
        }
    }

    protected abstract Encryptable<T> decryptWith(SecurityConfigurationMapping service) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException;

    @SuppressWarnings("rawtypes")
    protected SecurityConfiguration getSecurityConfig(SecurityConfigurationMapping service) {
        Class clazz = this.getClass();
        SecurityConfiguration config = service.get(clazz);
        if (config == null) {
            throw new NullPointerException("Security configuration is not defined for " + clazz.getCanonicalName());
        }
        return config;
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

    @JsonIgnore
    public boolean isEncrypted() {
        return encrypted;
    }
}
