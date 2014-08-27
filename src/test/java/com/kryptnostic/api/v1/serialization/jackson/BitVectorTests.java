package com.kryptnostic.api.v1.serialization.jackson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.linear.BitUtils;

public class BitVectorTests {

    private static final int LEN = 256;
    private final String LIST_SEPARATOR = ",";
    private static ObjectMapper mapper;

    @BeforeClass
    public static void init() {
        mapper = new KodexObjectMapperFactory().getObjectMapper(); 
    }

    @Test
    public void serializeBitvectorTest() throws JsonGenerationException, JsonMappingException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BitVector bv = BitUtils.randomVector(LEN);
        mapper.writeValue(out, bv);

        Assert.assertEquals(TestUtil.wrapQuotes(BitVectors.marshalBitvector(bv)), new String(out.toByteArray()));
    }

    @Test
    public void deserializeBitvectorTest() throws JsonGenerationException, JsonMappingException, IOException {
        BitVector bv = BitUtils.randomVector(LEN);
        String serialized = TestUtil.wrapQuotes(BitVectors.marshalBitvector(bv));
        BitVector out = mapper.readValue(serialized, BitVector.class);

        Assert.assertEquals(bv, out);
    }

    @Test
    public void serializeBitvectorCollectionTest() throws JsonGenerationException, JsonMappingException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Collection<BitVector> vectors = generateCollection(10);
        mapper.writeValue(out, vectors);

        String expectedResult = generateExpectedCollectionResult(vectors);

        Assert.assertEquals(expectedResult, new String(out.toByteArray()));
    }

    @Test
    public void deserializeBitvectorCollectionTest() throws JsonGenerationException, JsonMappingException, IOException {
        Collection<BitVector> vectors = generateCollection(10);
        String inputString = generateExpectedCollectionResult(vectors);

        Collection<BitVector> vals = Arrays.asList(mapper.readValue(inputString, BitVector[].class));

        Assert.assertEquals(vectors, vals);
    }

    private Collection<BitVector> generateCollection(int size) {
        Collection<BitVector> vectors = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            vectors.add(BitUtils.randomVector(LEN));
        }
        return vectors;
    }

    /**
     * Generate expected serialization result for collection of bitvectors
     * 
     * @param vectors
     * @return
     */
    private String generateExpectedCollectionResult(Collection<BitVector> vectors) {
        String expectedResult = "";
        Iterator<BitVector> iter = vectors.iterator();
        while (iter.hasNext()) {
            expectedResult += TestUtil.wrapQuotes(BitVectors.marshalBitvector(iter.next()));
            if (iter.hasNext()) {
                expectedResult += LIST_SEPARATOR;
            }
        }
        return "[" + expectedResult + "]";
    }
}
