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
        Metadatum one = new Metadatum( new DocumentId( "ABC", user) , "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadatum two = new Metadatum( new DocumentId("ABC",user) , "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadatum three = new Metadatum( new DocumentId("ABC1",user) , "ABC", Arrays.asList( 1, 2, 3 ) );
        Metadatum four = new Metadatum( new DocumentId("ABC",user) , "ABC1", Arrays.asList( 1, 2, 3 ) );
        Metadatum five = new Metadatum( new DocumentId("ABC", user) ,"ABC", Arrays.asList( 1, 2, 3, 4 ) );
        Metadatum six = new Metadatum( new DocumentId("ABC", user) ,"ABC", Arrays.asList( 1, 3, 2 ) );

        Assert.assertEquals( one, one );
        Assert.assertEquals( one, two );
        Assert.assertNotEquals( one, three );
        Assert.assertNotEquals( one, four );
        Assert.assertNotEquals( one, five );
        Assert.assertNotEquals( one, six );
    }

}
