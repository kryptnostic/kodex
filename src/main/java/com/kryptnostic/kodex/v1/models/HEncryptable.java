package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;

public class HEncryptable<T> extends Encryptable<T> {

    public HEncryptable(Ciphertext encryptedData, Ciphertext encryptedClassName) {
        super(encryptedData, encryptedClassName);
    }

    public HEncryptable(T data) {
        super(data);
    }

    @Override
    public Encryptable<T> encryptWith(Object key) throws JsonProcessingException {
        if (key instanceof PublicKey) {
            return hEncrypt((PublicKey) key);
        }
        throw new IllegalArgumentException("Key type not supported");
    }

    private Encryptable<T> hEncrypt(PublicKey key) throws JsonProcessingException {
        Ciphertext encryptedData = key.encryptIntoEnvelope(mapper.writeValueAsBytes(getData()));
        Ciphertext encryptedClassName = key.encryptIntoEnvelope(getClassName().getBytes());
        return new HEncryptable<T>(encryptedData, encryptedClassName);
    }

    @Override
    protected Encryptable<T> decryptWith(Object key) throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        if (key instanceof PrivateKey) {
            return hDecrypt((PrivateKey) key);
        }
        throw new IllegalArgumentException("Key type not supported");
    }

    private Encryptable<T> hDecrypt(PrivateKey key) throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        String className = StringUtils.newStringUtf8(key.decryptFromEnvelope(getEncryptedClassName()));
        byte[] objectBytes = key.decryptFromEnvelope(getEncryptedData());
        @SuppressWarnings("unchecked")
        T obj = mapper.<T> readValue(objectBytes,
                (Class<T>) Class.forName(className));
        return new HEncryptable<T>(obj);
    }
}
