package com.kryptnostic.crypto.v1.ciphers;

public interface ICryptoService {
    BlockCiphertext encrypt( byte[] plaintext );
    byte[] decrypt( BlockCiphertext ciphertext );

    void destroy();
}
