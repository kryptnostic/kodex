package com.kryptnostic.v2.storage.models;

import com.kryptnostic.utils.BaseJacksonSerializationTest;

public class LoadLevelsJacksonSerializationTest extends BaseJacksonSerializationTest<LoadLevel> {

    @Override
    protected LoadLevel getSampleData() {
        return LoadLevel.CIPHERTEXT;
    }

    @Override
    protected Class<LoadLevel> getClazz() {
        return LoadLevel.class;
    }

}
