package com.kryptnostic.utils;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public abstract class BaseJacksonSerializationTest<T> {
    private static final ObjectMapper mapper = KodexObjectMapperFactory.getObjectMapper();
    private static final ObjectMapper smile  = KodexObjectMapperFactory.getSmileMapper();

    @Test
    public void testSerdes() throws IOException {
        T data = getSampleData();
        String s = mapper.writeValueAsString( data );
        byte[] b = mapper.writeValueAsBytes( data );
        Assert.assertEquals( data, mapper.readValue( s, getClazz() ) );
        Assert.assertEquals( data, mapper.readValue( b, getClazz() ) );

        b = smile.writeValueAsBytes( data );
        Assert.assertEquals( data, smile.readValue( b, getClazz() ) );
    }

    protected abstract T getSampleData();

    protected abstract Class<T> getClazz();
}
