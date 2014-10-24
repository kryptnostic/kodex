package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class EncryptableTests {

    private static final int PRIVATE_KEY_BLOCK_SIZE = 64;
    private static final UserKey user = new UserKey("kryptnostic","tester");
    @Test
    public void encryptableStringConstructionTest() {
        String plain = "I am cool";
        Encryptable<String> plainString = new FheEncryptable<String>(plain);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(plain, plainString.getData());
    }

    @Test
    public void encryptableStringEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException, SecurityConfigurationException {
        String plain = "I am cool";
        Encryptable<String> plainString = new FheEncryptable<String>(plain);

        PrivateKey privateKey = new PrivateKey(PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE);
        PublicKey publicKey = new PublicKey(privateKey);

        SecurityConfigurationMapping config = new SecurityConfigurationMapping().add(FheEncryptable.class, publicKey)
                .add(FheEncryptable.class, privateKey);

        Encryptable<String> cipherString = plainString.encrypt(config);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(plain, plainString.getData());

        Assert.assertNotNull(cipherString.getEncryptedData());
        Assert.assertNotNull(cipherString.getEncryptedClassName());
        Assert.assertTrue(cipherString.isEncrypted());
        Assert.assertNull(cipherString.getData());

        Encryptable<String> decryptedString = cipherString.decrypt(config);
        Assert.assertNull(decryptedString.getEncryptedData());
        Assert.assertNull(decryptedString.getEncryptedClassName());
        Assert.assertFalse(decryptedString.isEncrypted());
        Assert.assertEquals(plain, decryptedString.getData());
    }

    @Test
    public void encryptableMetadatumConstructionTest() {
        Metadata m = new Metadata( new DocumentId( "ABC", user) ,  "ABC", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> plainString = new FheEncryptable<Metadata>(m);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(m, plainString.getData());
    }

    @Test
    public void encryptableMetadatumEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException, SecurityConfigurationException {
        Metadata m = new Metadata( new DocumentId( "ABC", user) , "ABC", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> plainString = new FheEncryptable<Metadata>(m);

        PrivateKey privateKey = new PrivateKey(PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE);
        PublicKey publicKey = new PublicKey(privateKey);

        SecurityConfigurationMapping config = new SecurityConfigurationMapping().add(FheEncryptable.class, publicKey)
                .add(FheEncryptable.class, privateKey);

        Encryptable<Metadata> cipherString = plainString.encrypt(config);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(m, plainString.getData());

        Assert.assertNotNull(cipherString.getEncryptedData());
        Assert.assertNotNull(cipherString.getEncryptedClassName());
        Assert.assertTrue(cipherString.isEncrypted());
        Assert.assertNull(cipherString.getData());

        Encryptable<Metadata> decryptedString = cipherString.decrypt(config);
        Assert.assertNull(decryptedString.getEncryptedData());
        Assert.assertNull(decryptedString.getEncryptedClassName());
        Assert.assertFalse(decryptedString.isEncrypted());
        Assert.assertEquals(m, (Metadata) decryptedString.getData());
    }

}
