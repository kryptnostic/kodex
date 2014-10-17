package com.kryptnostic.kodex.v1.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class AesEncryptableTests extends AesEncryptableBase {

    @Before
    public void setup() {
        initImplicitEncryption();
    }
    
    @Ignore
    @Test
    public void counterModeTest() {
        String plain = "I am cool";
        Encryptable<String> plainString = new AesEncryptable<String>(plain);

        Assert.assertNull(plainString.getEncryptedData());
        Assert.assertNull(plainString.getEncryptedClassName());
        Assert.assertFalse(plainString.isEncrypted());
        Assert.assertEquals(plain, plainString.getData());
    }
}
