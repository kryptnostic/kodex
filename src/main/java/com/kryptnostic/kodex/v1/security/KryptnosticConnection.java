package com.kryptnostic.kodex.v1.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.users.v1.UserKey;

public interface KryptnosticConnection {
    Kodex<String> getKodex();
    PrivateKey decryptPrivateKey( BlockCiphertext encryptedPrivateKey ) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException;
    BlockCiphertext encryptPrivateKey( PrivateKey privateKey ) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException;
    String getUserCredential();
    UserKey getUserKey();
    String getUrl();
}
