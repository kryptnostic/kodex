package com.kryptnostic.crypto.v1.keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kryptnostic.crypto.v1.ciphers.AesCryptoService;
import com.kryptnostic.crypto.v1.ciphers.BlockCiphertext;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;

public class CypherTests {
    private static CryptoService crypto;
    private static AesCryptoService aesCrypto;

    @BeforeClass
    public static void createCryptoService() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        crypto = new CryptoService( Cypher.AES_CTR_PKCS5_128 , "crypto-test".toCharArray() );
        aesCrypto = new AesCryptoService( Cypher.AES_CTR_PKCS5_128 );
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
    
    @Test
    public void testRawAesCrypto() throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidParameterSpecException {
        byte[] randomBytes = new byte[ 191 ] ;
        new Random().nextBytes( randomBytes );
        
       BlockCiphertext ciphertext = aesCrypto.encrypt( randomBytes , new byte[0] );
       AesCryptoService decryptor = new AesCryptoService( aesCrypto.getCypher() , aesCrypto.getSecretKey() ); 
       byte[] plaintext  = decryptor.decryptBytes( ciphertext );
       Assert.assertArrayEquals( randomBytes , plaintext );
    }   

}
