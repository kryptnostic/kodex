package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.utils.SerializationTestUtils;

public class MetadatumTests extends SerializationTestUtils {

    @Test
    public void equalsTest() {
        Metadata one = new Metadata( "ABC", "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata two = new Metadata( "ABC", "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata three = new Metadata( "ABC1", "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata four = new Metadata( "ABC", "ABC1", Arrays.asList( 1, 2, 3 ) );
        Metadata five = new Metadata( "ABC", "ABC", Arrays.asList( 1, 2, 3, 4 ) );
        Metadata six = new Metadata( "ABC", "ABC", Arrays.asList( 1, 3, 2 ) );

        Assert.assertEquals( one, one );
        Assert.assertEquals( one, two );
        Assert.assertNotEquals( one, three );
        Assert.assertNotEquals( one, four );
        Assert.assertNotEquals( one, five );
        Assert.assertNotEquals( one, six );
    }

    @Test
    public void constructionTest() {
        Metadata hasValidOffsets = new Metadata( "ABC", "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata hasInvalidOffsets = new Metadata( "ABC", "ABC", Arrays.asList( 0, -1, 1, 2, 3, -5 ) );

        Assert.assertEquals( Arrays.asList( 1, 2, 3 ), hasValidOffsets.getLocations() );
        Assert.assertEquals( Arrays.asList( 0, 1, 2, 3 ), hasInvalidOffsets.getLocations() );
    }
}
