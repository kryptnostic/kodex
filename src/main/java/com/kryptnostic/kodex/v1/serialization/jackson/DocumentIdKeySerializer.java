package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kryptnostic.sharing.v1.DocumentId;

public class DocumentIdKeySerializer extends JsonSerializer<DocumentId> {

    public static final String VALUE_SEPARATOR = ";";

    @Override
    public void serialize( DocumentId value, JsonGenerator jgen, SerializerProvider provider ) throws IOException,
            JsonProcessingException {
        String docId = value.getDocumentId();
        String realm = value.getUser().getRealm();
        String name = value.getUser().getName();

        String text = docId + VALUE_SEPARATOR + realm + VALUE_SEPARATOR + name;

        jgen.writeFieldName( text );
    }

}
