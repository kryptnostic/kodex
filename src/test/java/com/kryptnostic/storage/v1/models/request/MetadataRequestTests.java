package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;
import com.kryptnostic.linear.BitUtils;
import com.kryptnostic.storage.v1.models.request.IndexableMetadata;
import com.kryptnostic.storage.v1.models.request.MetadataRequest;

public class MetadataRequestTests extends BaseSerializationTest {

    private static final int LENGTH = 256;

    @Test
    public void serializationTest() throws JsonGenerationException, JsonMappingException, IOException {
        Collection<IndexableMetadata> metadata = Lists.newArrayList();
        BitVector key = BitUtils.randomVector(LENGTH);
        Metadatum data = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        IndexableMetadata entry = new IndexableMetadata(key, data);
        metadata.add(entry);
        MetadataRequest req = new MetadataRequest(metadata);

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key))
                + ",\"data\":{\"documentId\":" + wrapQuotes(data.getDocumentId()) + ",\"token\":"
                + wrapQuotes(data.getToken()) + ",\"locations\":[1,2,3]}}]}";
        
        String actual = serialize(req);

        Assert.assertEquals(expected, actual);
    }
    
//    @Test
//    public void serializationWithEncryptionTest() throws JsonGenerationException, JsonMappingException, IOException {
//        Collection<IndexableMetadata> metadata = Lists.newArrayList();
//        BitVector key = BitUtils.randomVector(LENGTH);
//        Metadatum data = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
//        IndexableMetadata entry = new IndexableMetadata(key, data);
//        metadata.add(entry);
//        MetadataRequest req = new EncryptedMetadataRequest(metadata);
//
//        String encryptedString = encrypt("{\"documentId\":" + wrapQuotes(data.getDocumentId()) + ",\"token\":"
//                + wrapQuotes(data.getToken()) + ",\"locations\":[1,2,3]}");
//        
//        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key))
//                + ",\"data\":" + wrapQuotes(encryptedString) + "}]}";
//        
//        String actual = serialize(req);
//
//        Assert.assertEquals(expected, actual);
//    }
}
