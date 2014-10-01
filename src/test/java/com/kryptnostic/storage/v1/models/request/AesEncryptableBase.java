package com.kryptnostic.storage.v1.models.request;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class AesEncryptableBase extends BaseSerializationTest {
    protected CryptoService crypto;
    protected SecurityConfigurationMapping config;
    
    protected void initImplicitEncryption() {
        // register key with object mapper
        this.crypto = new CryptoService(Cypher.AES_CTR_PKCS5_128, new BigInteger(130, new SecureRandom()).toString(32)
                .toCharArray());

        resetSecurityConfiguration();
    }
    
    protected void resetSecurityConfiguration() {
        this.config = new SecurityConfigurationMapping().add(AesEncryptable.class, this.crypto);
        this.mapper = new KodexObjectMapperFactory().getObjectMapper(config);
    }

}
