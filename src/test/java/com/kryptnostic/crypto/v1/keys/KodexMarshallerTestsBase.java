package com.kryptnostic.crypto.v1.keys;

import java.util.Arrays;

import org.junit.Assert;

public abstract class KodexMarshallerTestsBase<T> {
    protected AbstractKodexFactory<T> kodexMarshaller;

    public <S> void testFromBytes( byte[] bytes, S expected ) {
        T recovered = kodexMarshaller.fromBytes( bytes );
        Assert.assertEquals( expected, recovered );
    }

    public void testToBytes( byte[] expected, T object ) {
        byte[] recovered = kodexMarshaller.toBytes( object );
        Assert.assertTrue( Arrays.equals( expected, recovered ) );
    }
}
