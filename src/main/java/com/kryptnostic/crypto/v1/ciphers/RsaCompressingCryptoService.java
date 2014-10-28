package com.kryptnostic.crypto.v1.ciphers;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class RsaCompressingCryptoService extends RsaCompressingEncryptionService {
    private final PrivateKey privateKey;
    
    public RsaCompressingCryptoService( Cypher cypher, KeyPair pair ) throws SecurityConfigurationException {
        this(cypher, pair.getPrivate(),pair.getPublic());
    }
    
    public RsaCompressingCryptoService( Cypher cypher, PrivateKey privateKey , PublicKey publicKey ) throws SecurityConfigurationException {
        super( cypher, publicKey );
        this.privateKey = privateKey;
    }
    
    public <T> T decrypt( byte[] plaintext, Class<T> clazz ) throws IOException {
        return marshaller.fromBytes( plaintext, clazz );
    }

    public <T> T decrypt( byte[] ciphertext, TypeReference<T> clazz ) throws IOException, SecurityConfigurationException {
        return marshaller.fromBytes( Cyphers.decrypt( cypher, privateKey, ciphertext ) , clazz );
    }
}
