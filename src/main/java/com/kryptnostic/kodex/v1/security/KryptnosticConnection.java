package com.kryptnostic.kodex.v1.security;

import java.io.IOException;
import java.security.PrivateKey;

import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.users.v1.UserKey;

public interface KryptnosticConnection {
    Kodex<String> getKodex();

    void flushKodex() throws IOException;

    PrivateKey decryptPrivateKey( BlockCiphertext encryptedPrivateKey ) throws IrisException;

    BlockCiphertext encryptPrivateKey( PrivateKey privateKey ) throws IrisException;

    String getUserCredential();

    UserKey getUserKey();

    String getUrl();
}
