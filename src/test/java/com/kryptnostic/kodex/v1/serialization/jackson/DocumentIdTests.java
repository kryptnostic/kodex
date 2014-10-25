package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class DocumentIdTests extends BaseSerializationTest {

    @Test
    public void fromStringTest() throws JsonParseException, JsonMappingException, IOException {
        DocumentId expected = new DocumentId( "cool", new UserKey( "krypt", "sina" ) );
        DocumentId actual = DocumentId.fromString( expected.toString() );
        Assert.assertEquals( expected, actual );
    }
}
