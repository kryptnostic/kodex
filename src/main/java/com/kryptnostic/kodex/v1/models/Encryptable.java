package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Preconditions;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = Encryptable.FIELD_CLASS)
public abstract class Encryptable<T> implements Serializable {
    private static final long serialVersionUID = 5128167833341065251L;
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
    protected final Ciphertext encryptedData;
    @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME)
    protected final Ciphertext encryptedClassName;

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
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException,
            SecurityConfigurationException {
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

    protected abstract boolean canDecryptWith(SecurityConfigurationMapping mapping);

    public final Encryptable<T> encrypt(SecurityConfigurationMapping service) throws JsonProcessingException,
            SecurityConfigurationException {
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

    protected abstract Encryptable<T> encryptWith(SecurityConfigurationMapping service) throws JsonProcessingException,
            SecurityConfigurationException;

    public final Encryptable<T> decrypt(SecurityConfigurationMapping service) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException, SecurityConfigurationException {
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
            JsonMappingException, IOException, ClassNotFoundException, SecurityConfigurationException;

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
