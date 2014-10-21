package com.kryptnostic.storage.v1.models.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Charsets;
import com.google.common.primitives.Chars;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class AesEncryptableUtilsTests extends AesEncryptableBase {

    @Test
    public void platformAssumptionsTest() throws JsonProcessingException, SecurityConfigurationException {
        initImplicitEncryption();

        final int PADDING = 2;
        
        AesEncryptable<String> enc = new AesEncryptable<String>(StringUtils.newStringUtf16("".getBytes(Charsets.UTF_16)));
        Assert.assertEquals(0 + PADDING, enc.encrypt(config).getEncryptedData().getContents().length);
        
        enc = new AesEncryptable<String>(StringUtils.newStringUtf16("a".getBytes(Charsets.UTF_16)));
        Assert.assertEquals(2 + PADDING, enc.encrypt(config).getEncryptedData().getContents().length);
        
        enc = new AesEncryptable<String>(StringUtils.newStringUtf16("ab".getBytes(Charsets.UTF_16)));
        Assert.assertEquals(4 + PADDING, enc.encrypt(config).getEncryptedData().getContents().length);
    }

    @Test
    public void shortStringChunkTest() throws SecurityConfigurationException, IOException, ClassNotFoundException {
        initImplicitEncryption();
        List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString("cool", config);

        Assert.assertEquals(1, results.size());
        Assert.assertTrue(results.get(0).isEncrypted());
        Assert.assertEquals(4 * Chars.BYTES, results.get(0).getEncryptedData().getContents().length);
        Assert.assertEquals(4 * Chars.BYTES, results.get(0).decrypt(config).getData().getBytes().length);
    }

    @Test
    public void longStringChunkTest() throws JsonProcessingException, SecurityConfigurationException {
        initImplicitEncryption();
        String str = StringUtils.newStringUtf8(fillByteArray(4096));
        List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString(str, config);

        Assert.assertEquals(1, results.size());

        str = StringUtils.newStringUtf8(fillByteArray(4096 + 1));

        results = AesEncryptableUtils.chunkString(str, config);

        Assert.assertEquals(2, results.size());
    }

    private byte[] fillByteArray(int sz) {
        byte[] str = new byte[sz];

        for (int i = 0; i < sz; i++) {
            str[i] = 42;
        }

        return str;
    }

}
