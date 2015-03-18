package com.kryptnostic.kodex.v1.crypto.ciphers;

public interface ICryptoService {
    BlockCiphertext encrypt( byte[] plaintext );
    byte[] decrypt( BlockCiphertext ciphertext );

    void destroy();
}
