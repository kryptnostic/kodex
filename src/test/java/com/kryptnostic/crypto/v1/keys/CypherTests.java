package com.kryptnostic.crypto.v1.keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;

public class CypherTests {
    private static CryptoService crypto;

    @BeforeClass
    public static void createCryptoService() {
        crypto = new CryptoService( Cypher.AES_CTR_PKCS5_128 , "crypto-test".toCharArray() );
    }

    @Test
    public void cryptoTest() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException,
            InvalidAlgorithmParameterException {
        String expected = "hello world!";
        BlockCiphertext bc = crypto.encrypt( expected );
        String actual = crypto.decrypt( bc );
        Assert.assertEquals( expected , actual );
    }
}
