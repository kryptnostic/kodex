package com.kryptnostic.storage.v1.models.utils;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class AesEncryptableUtilsTests extends AesEncryptableBase {

    @Test
    public void shortStringChunkTest() throws JsonProcessingException, SecurityConfigurationException {
        initImplicitEncryption();
        List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString("cool", config);

        Assert.assertEquals(1, results.size());
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
