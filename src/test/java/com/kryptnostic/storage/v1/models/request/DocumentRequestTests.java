package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.DocumentBlock;

public class DocumentRequestTests extends AesEncryptableBase {

    @Test
    public void testSerializeDeserialize() throws JsonGenerationException, JsonMappingException, IOException,
            SecurityConfigurationException, ClassNotFoundException {
        initImplicitEncryption();

        DocumentRequest request = new DocumentRequest(AesEncryptableUtils.createEncryptedDocument("document1",
                "this is a cool document", this.config));
        String serialized = serialize(request);

        DocumentRequest recovered = deserialize(serialized, DocumentRequest.class);

        Assert.assertEquals(request.getDocument(), recovered.getDocument());
    }
}
