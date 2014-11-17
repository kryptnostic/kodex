package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.kryptnostic.sharing.v1.DocumentId;

public class DocumentIdKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey( String key, DeserializationContext ctxt ) throws IOException, JsonProcessingException {
        if ( key.length() == 0 ) { // [JACKSON-360]
            return null;
        }

        return DocumentId.fromId( key );
    }
}
