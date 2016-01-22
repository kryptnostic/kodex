package com.kryptnostic.v2.storage.models;

public enum LoadLevel {
    ID,
    METADATA,
    @Deprecated
    OLD_CRYPTO_SERVICE,
    CRYPTO_SERVICE,
    CIPHERTEXT
}
