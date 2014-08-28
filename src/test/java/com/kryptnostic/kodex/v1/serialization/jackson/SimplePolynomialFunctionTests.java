package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.linear.BitUtils;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SimplePolynomialFunctionTests extends BaseSerializationTest {
    private static final int LEN = 256;

    @Test
    public void serializeSpfTest() throws JsonGenerationException, JsonMappingException, IOException {
        SimplePolynomialFunction spf = PolynomialFunctions.randomFunction(LEN, LEN);

        String expected = wrapQuotes(PolynomialFunctions.marshalSimplePolynomialFunction(spf));

        Assert.assertEquals(expected, serialize(spf));
    }

    @Test
    public void deserializeSpfTest() throws JsonParseException, JsonMappingException, IOException {
        SimplePolynomialFunction spf = PolynomialFunctions.randomFunction(LEN, LEN);
        String serialized = wrapQuotes(PolynomialFunctions.marshalSimplePolynomialFunction(spf));
        SimplePolynomialFunction out = deserialize(serialized, SimplePolynomialFunction.class);
        Assert.assertEquals(spf, out);

        Assert.assertArrayEquals(spf.getContributions(), out.getContributions());
        Assert.assertArrayEquals(spf.getMonomials(), out.getMonomials());

        for (int i = 0; i < 10000; i++) {

            BitVector rando = BitUtils.randomVector(LEN);

            Assert.assertEquals(spf.apply(rando), out.apply(rando));
        }
    }

}
