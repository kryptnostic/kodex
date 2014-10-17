package com.kryptnostic.storage.v1.models.response;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentResponseTests extends AesEncryptableBase {

    // TODO add jackson annotation checker

    @Test
    public void testDeserialize() throws JsonGenerationException, JsonMappingException, IOException,
            SecurityConfigurationException, ClassNotFoundException {
        initImplicitEncryption();
        DocumentResponse resp = new DocumentResponse(AesEncryptableUtils.createEncryptedDocument("document1", "test cool thing",
                config), 200, true);

        String serialized = serialize(resp);

        DocumentResponse result = deserialize(serialized, DocumentResponse.class);

        Assert.assertEquals(resp.getData(), result.getData());
    }
}
