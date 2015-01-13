package com.kryptnostic.kodex.v1.serialization.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

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
public class DefaultChunkingStrategy implements ChunkingStrategy {

    @JsonCreator
    public DefaultChunkingStrategy() {

    }

    /**
     * This strategy will subdivide byte data into blocks of this length
     */
    public static final int BLOCK_LENGTH_IN_BYTES = 4096;

    @Override
    @JsonIgnore
    public <T> Iterable<byte[]> split( T object ) throws IOException {
        ObjectMapper mapper = Encryptable.getMapper();
        ByteBuffer plaintext = null;

        try {
            byte[] bytes = null;
            if ( object instanceof String ) {
                bytes = StringUtils.getBytesUtf8( (String) object );
            } else {
                bytes = mapper.writeValueAsBytes( object );
            }
            plaintext = ByteBuffer.wrap( bytes );
        } catch ( JsonProcessingException e ) {
            throw new IOException( e );
        }

        int remaining = plaintext.remaining();
        int remainingBlocks = (int) Math.ceil( (double) remaining / getLength() );
        List<byte[]> byteBlocks = Lists.newArrayListWithCapacity( remainingBlocks );

        // divide plain bytes into chunks of BLOCK_LENGTH_IN_BYTES, add these blocks to byteBlocks
        while ( remaining > 0 ) {
            byte[] block;
            // Re-allocate byte block each time as it will be handed off to list
            if ( remaining >= getLength() ) {
                block = new byte[ getLength() ];
            } else {
                block = new byte[ remaining ];
            }
            // move bytes and adjust cursors for buffer
            plaintext.get( block );
            byteBlocks.add( block );
            remaining = plaintext.remaining();
        }

        return byteBlocks;
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
        return BLOCK_LENGTH_IN_BYTES;
    }
}
