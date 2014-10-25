package com.kryptnostic.kodex.v1.security;

import java.security.PrivateKey;

import com.kryptnostic.users.v1.UserKey;

public interface CredentialService {
    PrivateKey decryptPrivateKey( byte[] encryptedPrivateKey );
    String getUserCredential();
    UserKey getUserKey();
}
