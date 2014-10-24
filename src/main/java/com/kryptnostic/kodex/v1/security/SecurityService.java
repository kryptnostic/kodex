package com.kryptnostic.kodex.v1.security;

import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.users.v1.UserKey;

public interface SecurityService {
    Kodex getKodex();

    String getUserCredential();

    UserKey getUserKey();
}
