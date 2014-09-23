package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;

public class MetadatumTests extends BaseSerializationTest {

    @Test
    public void equalsTest() {
        Metadatum one = new Metadatum("ABC", "ABC", Arrays.asList(1, 2, 3));
        Metadatum two = new Metadatum("ABC", "ABC", Arrays.asList(1, 2, 3));
        Metadatum three = new Metadatum("ABC1", "ABC", Arrays.asList(1, 2, 3));
        Metadatum four = new Metadatum("ABC", "ABC1", Arrays.asList(1, 2, 3));
        Metadatum five = new Metadatum("ABC", "ABC", Arrays.asList(1, 2, 3, 4));
        Metadatum six = new Metadatum("ABC", "ABC", Arrays.asList(1, 3, 2));
        
        Assert.assertEquals(one, one);
        Assert.assertEquals(one, two);
        Assert.assertNotEquals(one, three);
        Assert.assertNotEquals(one, four);
        Assert.assertNotEquals(one, five);
        Assert.assertNotEquals(one, six);
    }
    
}
