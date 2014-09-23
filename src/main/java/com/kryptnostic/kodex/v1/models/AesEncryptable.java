package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class AesEncryptable<T> extends Encryptable<T> {

    private final static ObjectMapper mapper = ( new KodexObjectMapperFactory() ).getObjectMapper(null);

    public AesEncryptable(T data) {
        super(data);
    }

    public AesEncryptable(BlockCiphertext ciphertext, BlockCiphertext className) {
        super(ciphertext, className);
    }

    @JsonCreator
    public AesEncryptable(@JsonProperty(FIELD_ENCRYPTED_DATA) BlockCiphertext ciphertext,
            @JsonProperty(FIELD_ENCRYPTED_CLASS_NAME) BlockCiphertext className,
            @JacksonInject SecurityConfigurationMapping mapping) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        super(ciphertext, className, mapping);
    }

    @Override
    protected Encryptable<T> encryptWith(SecurityConfigurationMapping service) throws JsonProcessingException,
            SecurityConfigurationException {
        @SuppressWarnings("rawtypes")
        CryptoService crypto = service.get(AesEncryptable.class, CryptoService.class);

        BlockCiphertext encryptedData = null;
        BlockCiphertext encryptedClassName = null;
        try {
            encryptedData = crypto.encrypt(mapper.writeValueAsString(getData()));
            encryptedClassName = crypto.encrypt(getClassName());
        } catch (InvalidKeyException e) {
            wrapSecurityConfigurationException(e);
        } catch (InvalidKeySpecException e) {
            wrapSecurityConfigurationException(e);
        } catch (NoSuchAlgorithmException e) {
            wrapSecurityConfigurationException(e);
        } catch (NoSuchPaddingException e) {
            wrapSecurityConfigurationException(e);
        } catch (IllegalBlockSizeException e) {
            wrapSecurityConfigurationException(e);
        } catch (BadPaddingException e) {
            wrapSecurityConfigurationException(e);
        } catch (InvalidParameterSpecException e) {
            wrapSecurityConfigurationException(e);
        }
        return new AesEncryptable<T>(encryptedData, encryptedClassName);

    }

    private void wrapSecurityConfigurationException(Exception e) throws SecurityConfigurationException {
        throw new SecurityConfigurationException("Error occurred while trying to encrypt or decrypt data.", e);
    }

    @Override
    protected Encryptable<T> decryptWith(SecurityConfigurationMapping service) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException, SecurityConfigurationException {
        CryptoService crypto = service.get(AesEncryptable.class, CryptoService.class);
        String className = null;
        String objectString = null;
        try {
            className = crypto.decrypt((BlockCiphertext) getEncryptedClassName());
            objectString = crypto.decrypt((BlockCiphertext) getEncryptedData());
        } catch (InvalidKeyException e) {
            wrapSecurityConfigurationException(e);
        } catch (InvalidAlgorithmParameterException e) {
            wrapSecurityConfigurationException(e);
        } catch (NoSuchAlgorithmException e) {
            wrapSecurityConfigurationException(e);
        } catch (NoSuchPaddingException e) {
            wrapSecurityConfigurationException(e);
        } catch (InvalidKeySpecException e) {
            wrapSecurityConfigurationException(e);
        } catch (IllegalBlockSizeException e) {
            wrapSecurityConfigurationException(e);
        } catch (BadPaddingException e) {
            wrapSecurityConfigurationException(e);
        }

        @SuppressWarnings("unchecked")
        T obj = mapper.<T> readValue(objectString, (Class<T>) Class.forName(className));
        return new AesEncryptable<T>(obj);
    }

    @Override
    protected Encryptable<T> createEncrypted(Ciphertext ciphertext, Ciphertext className) {
        return new AesEncryptable<T>((BlockCiphertext) ciphertext, (BlockCiphertext) className);
    }

    @Override
    protected boolean canDecryptWith(SecurityConfigurationMapping mapping) {
        if (mapping == null) {
            return false;
        }
        return mapping.contains(AesEncryptable.class, CryptoService.class);
    }

}
