package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.models.HEncryptable;

public class EncryptableTests {

    private static final int PRIVATE_KEY_BLOCK_SIZE = 64;

    @Test
    public void hencryptableStringConstructionTest() {
        String plain = "I am cool";
        Encryptable<String> plainString = new HEncryptable<String>(plain);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(plain, plainString.getData());
    }

    @Test
    public void hencryptableStringEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        String plain = "I am cool";
        Encryptable<String> plainString = new HEncryptable<String>(plain);

        PrivateKey privateKey = new PrivateKey(PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE);
        PublicKey publicKey = new PublicKey(privateKey);

        Encryptable<String> cipherString = plainString.encrypt(publicKey);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(plain, plainString.getData());

        Assert.assertNotNull(cipherString.getEncryptedData());
        Assert.assertNotNull(cipherString.getEncryptedClassName());
        Assert.assertTrue(cipherString.isEncrypted());
        Assert.assertNull(cipherString.getData());

        Encryptable<String> decryptedString = cipherString.decrypt(privateKey);
        Assert.assertNull(decryptedString.getEncryptedData());
        Assert.assertNull(decryptedString.getEncryptedClassName());
        Assert.assertFalse(decryptedString.isEncrypted());
        Assert.assertEquals(plain, decryptedString.getData());
    }

    @Test
    public void hencryptableMetadatumConstructionTest() {
        Metadatum m = new Metadatum("ABC", "ABC", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> plainString = new HEncryptable<Metadatum>(m);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(m, plainString.getData());
    }

    @Test
    public void hencryptableMetadatumEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException {
        Metadatum m = new Metadatum("ABC", "ABC", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> plainString = new HEncryptable<Metadatum>(m);

        PrivateKey privateKey = new PrivateKey(PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE);
        PublicKey publicKey = new PublicKey(privateKey);

        Encryptable<Metadatum> cipherString = plainString.encrypt(publicKey);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(m, plainString.getData());

        Assert.assertNotNull(cipherString.getEncryptedData());
        Assert.assertNotNull(cipherString.getEncryptedClassName());
        Assert.assertTrue(cipherString.isEncrypted());
        Assert.assertNull(cipherString.getData());

        Encryptable<Metadatum> decryptedString = cipherString.decrypt(privateKey);
        Assert.assertNull(decryptedString.getEncryptedData());
        Assert.assertNull(decryptedString.getEncryptedClassName());
        Assert.assertFalse(decryptedString.isEncrypted());
        Assert.assertEquals(m, (Metadatum) decryptedString.getData());

    }

}
