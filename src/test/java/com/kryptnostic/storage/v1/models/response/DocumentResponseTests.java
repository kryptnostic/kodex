package com.kryptnostic.storage.v1.models.response;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentResponseTests extends AesEncryptableBase {

    // TODO add reflexive jackson annotation checker 
    
    @Test
    public void testDeserialize() throws JsonGenerationException, JsonMappingException, IOException {
        initImplicitEncryption();
        DocumentResponse resp = new DocumentResponse(new AesEncryptable<String>("this is my body"), 200, true);

        String serialized = serialize(resp);

        DocumentResponse result = deserialize(serialized, DocumentResponse.class);
        
        Assert.assertEquals(resp.getData().getBody().getData(), result.getData().getBody().getData());
    }
}
