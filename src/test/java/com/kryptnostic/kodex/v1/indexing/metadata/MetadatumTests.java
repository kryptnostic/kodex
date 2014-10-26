package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class MetadatumTests extends BaseSerializationTest {

    @Test
    public void equalsTest() {
        UserKey user = new UserKey( "kryptnostic", "tester" );
        Metadata one = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata two = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata three = new Metadata( new DocumentId( "ABC1", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata four = new Metadata( new DocumentId( "ABC", user ), "ABC1", Arrays.asList( 1, 2, 3 ) );
        Metadata five = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3, 4 ) );
        Metadata six = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 3, 2 ) );

        Assert.assertEquals( one, one );
        Assert.assertEquals( one, two );
        Assert.assertNotEquals( one, three );
        Assert.assertNotEquals( one, four );
        Assert.assertNotEquals( one, five );
        Assert.assertNotEquals( one, six );
    }

    @Test
    public void constructionTest() {
        UserKey user = new UserKey( "kryptnostic", "tester" );
        Metadata hasValidOffsets = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadata hasInvalidOffsets = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList(
                0,
                -1,
                1,
                2,
                3,
                -5 ) );

        Assert.assertEquals( Arrays.asList( 1, 2, 3 ), hasValidOffsets.getLocations() );
        Assert.assertEquals( Arrays.asList( 0, 1, 2, 3 ), hasInvalidOffsets.getLocations() );
    }
}
