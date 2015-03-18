package com.kryptnostic.kodex.v1.serialization.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kryptnostic.kodex.v1.models.blocks.ChunkingStrategy;

/**
 * @author sinaiman
 *
 */
public class JsonChunkingStrategy implements ChunkingStrategy {

    @JsonCreator
    public JsonChunkingStrategy() {

    }

    @Override
    @JsonIgnore
    /**
     * Note: this doesn't split anything
     */
    public <T> Iterable<byte[]> split( T object ) throws IOException {
        ObjectMapper mapper = Encryptable.getMapper();
        byte[] bytes = null;

        try {
            if ( object instanceof String ) {
                bytes = StringUtils.getBytesUtf8( (String) object );
            } else {
                bytes = mapper.writeValueAsBytes( object );
            }
        } catch ( JsonProcessingException e ) {
            throw new IOException( e );
        }

        return Lists.newArrayList( bytes );
    }

    @Override
    @JsonIgnore
    public <T> T join( Iterable<byte[]> blocks, Class<T> klass ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for ( byte[] b : blocks ) {
            baos.write( b );
        }

        ObjectMapper mapper = Encryptable.getMapper();
        T plainData = null;
        if ( klass.isAssignableFrom( String.class ) ) {
            plainData = (T) StringUtils.newStringUtf8( baos.toByteArray() );
        } else {
            plainData = mapper.<T> readValue( baos.toByteArray(), klass );
        }

        return plainData;
    }

    @Override
    @JsonIgnore
    public int getLength() {
        return -1;
    }

}
