package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class JacksonTypeRefKodexMarshaller<T> implements KodexMarshaller<T> {
    private final TypeReference<T> reference;
    private final ObjectMapper     mapper;

    public JacksonTypeRefKodexMarshaller( TypeReference<T> reference ) {
        this( reference, KodexObjectMapperFactory.getObjectMapper() );
    }

    public JacksonTypeRefKodexMarshaller( TypeReference<T> reference, ObjectMapper mapper ) {
        this.reference = reference;
        this.mapper = mapper;
    }

    @Override
    public T fromBytes( byte[] bytes ) throws IOException {
        return mapper.readValue( bytes, reference );
    }

    @Override
    public byte[] toBytes( T object ) throws IOException {
        return mapper.writeValueAsBytes( object );
    }

}
