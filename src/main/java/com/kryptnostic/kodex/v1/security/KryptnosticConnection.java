package com.kryptnostic.kodex.v1.security;

import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.users.v1.UserKey;

public interface KryptnosticConnection {
    Kodex<String> getKodex();

    PrivateKey getFhePrivateKey();

    PublicKey getFhePublicKey();

    String getUserCredential();

    UserKey getUserKey();

    String getUrl();

    EncryptedSearchPrivateKey getEncryptedSearchPrivateKey();
}
