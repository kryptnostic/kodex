package com.kryptnostic.storage.v1.models;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentTests extends AesEncryptableBase {

    @Test
    public void testEquals() throws SecurityConfigurationException, IOException, ClassNotFoundException {
        initImplicitEncryption();

        Document d1 = AesEncryptableUtils.createEncryptedDocument("document1", "cool document", config);
        Document d2 = AesEncryptableUtils.createEncryptedDocument("document1", "cool document", config);
        
        Assert.assertEquals(d1, d1);
        
        // these will have different verification hashes
        Assert.assertNotEquals(d1, d2);

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

        Assert.assertEquals(doc.getBlocks().getBlocks().get(0).getBlock().decrypt(config).getData(), result.getBlocks()
                .get(0).getBlock().decrypt(config).getData());
    }

    @Test
    public void testDocumentVerification() throws SecurityConfigurationException, IOException, ClassNotFoundException {
        initImplicitEncryption();

        HashFunction hashFunction = Hashing.sha256();

        Document doc = AesEncryptableUtils.createEncryptedDocument("test", "this is a test", config);

        String verify = doc.getMetadata().getVerify();

        Collection<DocumentBlock> blocks = doc.getBlocks().getBlocks();
        String hash = "";
        for (DocumentBlock block : blocks) {
            Encryptable<String> encryptableString = block.getBlock();
            hash += hashFunction.hashBytes(encryptableString.getEncryptedData().getContents()).toString();
        }

        String hashString = hashFunction.hashString(hash, Charsets.UTF_8).toString();

        Assert.assertEquals(hashString, verify);
    }
}
