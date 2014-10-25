package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class AesEncryptableTests extends AesEncryptableBase {

    @Before
    public void setup() throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException, SealedKodexException, IOException {
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
