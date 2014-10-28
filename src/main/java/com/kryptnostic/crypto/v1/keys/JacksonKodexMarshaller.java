package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class JacksonKodexMarshaller<T> implements KodexMarshaller<T> {
    private final Class<T>     clazz;
    private final ObjectMapper mapper;
//    protected static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;

    public JacksonKodexMarshaller( Class<T> clazz ) {
        this( clazz, KodexObjectMapperFactory.getObjectMapper() );
    }

    public JacksonKodexMarshaller( Class<T> clazz, ObjectMapper mapper ) {
        this.clazz = clazz;
        this.mapper = mapper;
    }

    @Override
    public T fromBytes( byte[] bytes ) throws IOException {
        final Inflater inflater = new Inflater();
        ByteBuffer in = ByteBuffer.wrap( bytes );
        int uncompressedLength = in.getInt();

        byte[] compressedBytes = new byte[ bytes.length - Integer.BYTES ];
        byte[] uncompressedBytes = new byte[ uncompressedLength ];
        in.get( compressedBytes );

        inflater.setInput( compressedBytes );
        int resultingLength = 0;
        try {
            resultingLength = inflater.inflate( uncompressedBytes );
        } catch ( DataFormatException e ) {
            throw new IOException( e );
        }
        Preconditions.checkState(
                resultingLength == uncompressedLength,
                "Expected length and decompressed length do not match." );
        return mapper.readValue( uncompressedBytes, clazz );
    }

    @Override
    public byte[] toBytes( T object ) throws IOException {
        final Deflater deflater = new Deflater( Deflater.BEST_COMPRESSION );
        byte[] input = mapper.writeValueAsBytes( object );
        byte[] output = new byte[ input.length << 1 ];
        deflater.setInput( input );
        deflater.finish();
        int compressedBytes = deflater.deflate( output, 0, output.length, Deflater.FULL_FLUSH );
        ByteBuffer o = ByteBuffer.allocate( Integer.BYTES + compressedBytes );
        o.putInt( input.length );
        o.put( output, 0, compressedBytes );
        return o.array();
    }

}
