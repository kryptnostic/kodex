package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A KodexFactory implemented with Jackson serialization.
 * 
 * @author Nick Hewitt
 */
public final class JacksonKodexFactory<T> extends AbstractKodexFactory<T> {
    private static final Logger logger = LoggerFactory.getLogger( JacksonKodexFactory.class );
    private final ObjectMapper mapper;
    private final Class<T> typeParameterlessClass;

    public JacksonKodexFactory( ObjectMapper mapper, Class<T> typeClass ) {
        this.mapper = mapper;
        this.typeParameterlessClass = typeClass;
    }

    @Override
    public T fromBytes( byte[] bytes ) {
        try {
            return mapper.readValue( bytes, typeParameterlessClass );
        } catch ( JsonParseException e ) {
            logger.error( e.getMessage() );
        } catch ( JsonMappingException e ) {
            logger.error( e.getMessage() );
        } catch ( IOException e ) {
            logger.error( e.getLocalizedMessage() );
        }
        return null;
    }

    @Override
    public byte[] toBytes( T object ) {
        try {
            return this.mapper.writeValueAsBytes( object );
        } catch ( JsonProcessingException e ) {
            logger.error( e.getLocalizedMessage() );
        }
        return null;
    }

}
