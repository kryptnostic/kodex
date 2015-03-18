package com.kryptnostic.kodex.v1.marshalling;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class DeflatingJacksonMarshaller  {
    private final ObjectMapper mapper = KodexObjectMapperFactory.getObjectMapper();
    protected static final int INTEGER_BYTES = Integer.SIZE / Byte.SIZE;

    public <T> T fromBytes( byte[] bytes , Class<T> reference) throws IOException {
        final Inflater inflater = new Inflater();
        ByteBuffer in = ByteBuffer.wrap( bytes );
        int uncompressedLength = in.getInt();

        byte[] compressedBytes = new byte[ bytes.length - INTEGER_BYTES ];
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
        return mapper.readValue( uncompressedBytes, reference );
    }
    
    public<T> T fromBytes( byte[] bytes , TypeReference<T> reference) throws IOException {
        final Inflater inflater = new Inflater();
        ByteBuffer in = ByteBuffer.wrap( bytes );
        int uncompressedLength = in.getInt();

        byte[] compressedBytes = new byte[ bytes.length - INTEGER_BYTES ];
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
        return mapper.readValue( uncompressedBytes, reference );
    }

    public byte[] toBytes( Object object ) throws IOException {
        final Deflater deflater = new Deflater( Deflater.BEST_COMPRESSION );
        byte[] input = mapper.writeValueAsBytes( object );
        byte[] output = new byte[ input.length << 1 ];
        deflater.setInput( input );
        deflater.finish();
        int compressedBytes = deflater.deflate( output, 0, output.length, Deflater.FULL_FLUSH );
        ByteBuffer o = ByteBuffer.allocate( INTEGER_BYTES + compressedBytes );
        o.putInt( input.length );
        o.put( output, 0, compressedBytes );
        return o.array();
    }

}
