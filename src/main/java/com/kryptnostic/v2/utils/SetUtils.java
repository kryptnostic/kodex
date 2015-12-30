package com.kryptnostic.v2.utils;

import com.google.common.primitives.Ints;

public class SetUtils {

    public static int expectedSize( int expectedSize ) {
        if ( expectedSize < 0 ) {
            throw new IllegalArgumentException( "expectedSize cannot be negative but was: " + expectedSize );
        }
        if ( expectedSize < 3 ) {
            return expectedSize + 1;
        }
        if ( expectedSize < Ints.MAX_POWER_OF_TWO ) {
            return expectedSize + expectedSize / 3;
        }
        return Integer.MAX_VALUE;
    }
}
