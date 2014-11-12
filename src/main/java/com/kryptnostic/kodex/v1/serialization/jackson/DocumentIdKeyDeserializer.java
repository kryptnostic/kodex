package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.google.gson.JsonParseException;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class DocumentIdKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey( String key, DeserializationContext ctxt ) throws IOException, JsonProcessingException {
        if ( key.length() == 0 ) { // [JACKSON-360]
            return null;
        }
        String[] vals = key.split( DocumentIdKeySerializer.VALUE_SEPARATOR );

        if ( vals.length != 3 ) {
            throw new JsonParseException(
                    "DocumentId key deserialization failed because the key string only contained " + vals.length
                            + " readable value, but 3 are expected. The malformed key was \"" + key + "\"" );
        }

        return DocumentId.fromIdAndUser( vals[ 0 ], new UserKey( vals[ 1 ], vals[ 2 ] ) );
    }
}
