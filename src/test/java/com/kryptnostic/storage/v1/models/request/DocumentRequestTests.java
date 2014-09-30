package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.storage.v1.models.Document;

public class DocumentRequestTests extends AesEncryptableBase {

    @Test
    public void testSerializeDeserialize() throws JsonGenerationException, JsonMappingException, IOException {
        initImplicitEncryption();

        DocumentRequest request = new DocumentRequest(new Document(new AesEncryptable<String>("a test body.")));
        String serialized = serialize(request);

        DocumentRequest recovered = deserialize(serialized, DocumentRequest.class);

        Assert.assertEquals(request.getDocument().getBody().getData(), recovered.getDocument().getBody().getData());
    }
}
