package com.kryptnostic.crypto.v1.keys;

import java.util.Arrays;

import org.junit.Assert;

public abstract class KodexFactoryTestsBase<T> {
    private KodexFactory<T> kodexFactory;

    public <S> void testFromBytes( byte[] bytes, S expected ) {
        T recovered = kodexFactory.fromBytes( bytes );
        Assert.assertEquals( expected, recovered );
    }

    public void testToBytes( byte[] expected, T object ) {
        byte[] recovered = kodexFactory.toBytes( object );
        Assert.assertTrue( Arrays.equals( expected, recovered ) );
    }
}
