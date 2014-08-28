package com.kryptnostic.storage.v1.models.response;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;

public class DocumentResponseTests extends BaseSerializationTest {

    @Test
    public void testSerialize() throws JsonGenerationException, JsonMappingException, IOException {
        DocumentResponse resp = new DocumentResponse("this is my body", 200, true);

        String expected = "{\"data\":{\"body\":\"this is my body\"},\"status\":200,\"success\":true}";

        Assert.assertEquals(expected, serialize(resp));
    }
    
    @Test
    public void testDeerialize() throws JsonGenerationException, JsonMappingException, IOException {
        DocumentResponse resp = new DocumentResponse("this is my body", 200, true);

        String expected = "{\"data\":{\"body\":\"this is my body\"},\"status\":200,\"success\":true}";

        DocumentResponse result = deserialize(expected, DocumentResponse.class);
        
        Assert.assertEquals(resp.getData(), result.getData());
    }
}
