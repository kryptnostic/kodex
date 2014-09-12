package com.kryptnostic.crypto.v1.ciphers;


/**
 * Holds the output of performing an AES encryption with {@link CryptoService}
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public class BlockCiphertext {
    //TODO: Add Json serialization info.
    private final byte[] iv;
    private final byte[] salt;
    private final byte[] ciphertext;
    private final byte[] encryptedLength;
    public BlockCiphertext( byte[] iv , byte[] salt, byte[] ciphertext, byte[] encryptedLength ) {
        this.iv = iv;
        this.salt = salt;
        this.ciphertext = ciphertext;
        this.encryptedLength = encryptedLength;
    }
    
    public byte[] getIv() {
        return iv;
    }
    public byte[] getSalt() {
        return salt;
    }
    public byte[] getCiphertext() {
        return ciphertext;
    }
    public byte[] getEncryptedLength() {
        return encryptedLength;
    }
}
