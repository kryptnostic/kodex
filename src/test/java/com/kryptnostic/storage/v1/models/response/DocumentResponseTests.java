package com.kryptnostic.storage.v1.models.response;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.storage.v1.models.Document;
import com.kryptnostic.storage.v1.models.DocumentMetadata;
import com.kryptnostic.utils.SecurityConfigurationTestUtils;

@SuppressWarnings( "javadoc" )
public class DocumentResponseTests extends SecurityConfigurationTestUtils {

    // TODO add jackson annotation checker

    @Test
    public void testDeserialize() throws JsonGenerationException, JsonMappingException, IOException {
        initializeCryptoService();

        loader.put( "document1", crypto );

        DocumentResponse resp = new DocumentResponse( new Document(
                new DocumentMetadata( "document1" ),
                "test cool thing" ), 200, true );

        String serialized = serialize( resp );

        DocumentResponse result = deserialize( serialized, DocumentResponse.class );

        Assert.assertEquals( resp.getData(), result.getData() );
    }
}
