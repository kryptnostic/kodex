package com.kryptnostic.storage.v1.models;

import java.io.IOException;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils.VerifiedStringBlocks;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentTests extends AesEncryptableBase {

    @Test
    public void testEquals() throws SecurityConfigurationException, IOException, ClassNotFoundException {
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

    @Test
    public void testDocumentSerialization() throws SecurityConfigurationException, IOException, ClassNotFoundException {
        initImplicitEncryption();

        Document doc = AesEncryptableUtils.createEncryptedDocument("test", "this is a test", config);
        String out = serialize(doc);
        Document result = deserialize(out, Document.class);

        Assert.assertEquals(doc, result);
    }

    @Test
    public void testDocumentBlockSerialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        initImplicitEncryption();

        Document doc = AesEncryptableUtils.createEncryptedDocument("test", "this is a test", config);
        String out = serialize(doc.getBlocks());
        DocumentBlocks result = deserialize(out, DocumentBlocks.class);

        Assert.assertEquals(doc.getBlocks().get(0).getBlock().decrypt(config).getData(), result.get(0).getBlock()
                .decrypt(config).getData());
    }
}
