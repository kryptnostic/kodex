package com.kryptnostic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class BaseSerializationTest {

    protected ObjectMapper mapper;

    @Before
    public void init() {
        mapper = new KodexObjectMapperFactory().getObjectMapper(null);
    }

    protected String wrapQuotes(String m) {
        return "\"" + m + "\"";
    }

    protected <T> String serialize(T val) throws JsonGenerationException, JsonMappingException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, val);
        return out.toString();
    }

    @SuppressWarnings("unchecked")
    protected <T> T deserialize(String in, @SuppressWarnings("rawtypes") Class type) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) mapper.readValue(in, type);
    }
}
