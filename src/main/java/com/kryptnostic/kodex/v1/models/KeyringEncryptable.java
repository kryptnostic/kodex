package com.kryptnostic.kodex.v1.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class KeyringEncryptable<T> extends Encryptable<T> {
    private static final long serialVersionUID = -1449670245836179256L;

    public KeyringEncryptable( T data ) {
        super( data );
    }

    public KeyringEncryptable( Ciphertext ciphertext, Ciphertext className ) {
        super( ciphertext, className );
    }

    @Override
    protected Encryptable<T> createEncrypted( Ciphertext ciphertext, Ciphertext className ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean canDecryptWith( Kodex<String> kodex ) throws SecurityConfigurationException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected Encryptable<T> encryptWith( Kodex<String> kodex ) throws JsonProcessingException,
            SecurityConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Encryptable<T> decryptWith( Kodex<String> kodex ) throws JsonParseException, JsonMappingException,
            IOException, ClassNotFoundException, SecurityConfigurationException {
        // TODO Auto-generated method stub
        return null;
    }
}
