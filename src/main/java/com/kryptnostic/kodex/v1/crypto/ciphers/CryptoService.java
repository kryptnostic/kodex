package com.kryptnostic.kodex.v1.crypto.ciphers;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public interface CryptoService {

    BlockCiphertext encrypt( byte[] bytes ) throws SecurityConfigurationException;

    BlockCiphertext encrypt( byte[] bytes, byte[] salt ) throws SecurityConfigurationException;

    byte[] decryptBytes( BlockCiphertext ciphertext ) throws SecurityConfigurationException;
    
    byte[] getSecretKey();
    
    abstract Cypher getCypher();

}