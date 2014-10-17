package com.kryptnostic.storage.v1.models;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentTests extends AesEncryptableBase {

    @Test
    public void equalsTest() throws SecurityConfigurationException, IOException, ClassNotFoundException {
        initImplicitEncryption();
        
        Document d1 = AesEncryptableUtils.createEncryptedDocument("document1", "cool document", config);
        Document d2 = AesEncryptableUtils.createEncryptedDocument("document1", "cool document", config);
        Assert.assertEquals(d1, d2);

        Document d3 = AesEncryptableUtils.createEncryptedDocument("document2", "cool document", config);
        Assert.assertNotEquals(d1, d3);

        Document d4 = AesEncryptableUtils.createEncryptedDocument("document1", "cool document cool", config);
        Assert.assertNotEquals(d1, d4);
        Assert.assertNotEquals(d1, d3);
    }
}
