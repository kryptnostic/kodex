package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;

public class JacksonKodexMarshallerTests {
    ObjectMapper mapper = (new KodexObjectMapperFactory()).getObjectMapper();
    KodexMarshaller<PrivateKey> marshaller = new JacksonKodexMarshaller<PrivateKey>( PrivateKey.class, mapper );
    private static final int PRIVATE_KEY_BLOCK_SIZE = 64;
    PrivateKey object = new PrivateKey( PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE);

    @Test
    public void testFromBytes( ) throws IOException {
        byte[] bytes = mapper.writeValueAsBytes( object );
        PrivateKey actual = marshaller.fromBytes( bytes );
        Assert.assertEquals( object, actual );
    }

    @Test
    public void testToBytes(  ) throws IOException {
        
        byte[] expected = mapper.writeValueAsBytes( object );
        byte[] actual = marshaller.toBytes( object );   
        Assert.assertTrue( Arrays.equals( expected, actual ) );
    }
    
    @Test
    public void testFromToBytes() throws IOException {
        byte[] bytes = marshaller.toBytes( object );
        PrivateKey actual = marshaller.fromBytes( bytes );
        Assert.assertEquals( object, actual );
    }

}
