package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class JacksonKodexMarshaller<T> implements KodexMarshaller<T> {
    private final Class<T>     clazz;
    private final ObjectMapper mapper;

    public JacksonKodexMarshaller( Class<T> clazz ) {
        this( clazz, KodexObjectMapperFactory.getObjectMapper() );
    }

    public JacksonKodexMarshaller( Class<T> clazz, ObjectMapper mapper ) {
        this.clazz = clazz;
        this.mapper = mapper;
    }

    @Override
    public T fromBytes( byte[] bytes ) throws IOException {
        return mapper.readValue( bytes, clazz );
    }

    @Override
    public byte[] toBytes( T object ) throws IOException{
        return mapper.writeValueAsBytes( object );
    }

}
