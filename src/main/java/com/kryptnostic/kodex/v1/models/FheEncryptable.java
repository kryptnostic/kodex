package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class FheEncryptable<T> extends Encryptable<T> {

    private final static ObjectMapper mapper = ( new KodexObjectMapperFactory() ).getObjectMapper(null);

    public FheEncryptable(T data) {
        super(data);
    }

    public FheEncryptable(Ciphertext ciphertext, Ciphertext className) {
        super(ciphertext, className);
    }

    @JsonCreator
    public FheEncryptable(@JsonProperty(FIELD_ENCRYPTED_DATA) Ciphertext ciphertext,
            @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME) Ciphertext className,
            @JacksonInject SecurityConfigurationMapping mapping) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        super(ciphertext, className, mapping);
    }

    @Override
    protected Encryptable<T> encryptWith(SecurityConfigurationMapping service) throws JsonProcessingException {
        PublicKey key = service.get(FheEncryptable.class, PublicKey.class);
        if (key == null) {
            throw new NullPointerException("No public key found");
        }
        Ciphertext encryptedData = key.encryptIntoEnvelope(mapper.writeValueAsBytes(getData()));
        Ciphertext encryptedClassName = key.encryptIntoEnvelope(getClassName().getBytes());
        return new FheEncryptable<T>(encryptedData, encryptedClassName);
    }

    @Override
    protected Encryptable<T> decryptWith(SecurityConfigurationMapping service) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException {
        PrivateKey key = service.get(FheEncryptable.class, PrivateKey.class);
        String className = StringUtils.newStringUtf8(key.decryptFromEnvelope(getEncryptedClassName()));
        byte[] objectBytes = key.decryptFromEnvelope(getEncryptedData());
        @SuppressWarnings("unchecked")
        T obj = mapper.<T> readValue(objectBytes, (Class<T>) Class.forName(className));
        return new FheEncryptable<T>(obj);
    }

    @Override
    protected Encryptable<T> createEncrypted(Ciphertext ciphertext, Ciphertext className) {
        return new FheEncryptable<T>(ciphertext, className);
    }

    @Override
    protected boolean canDecryptWith(SecurityConfigurationMapping mapping) {
        return mapping.contains(FheEncryptable.class, PrivateKey.class);
    }

}
