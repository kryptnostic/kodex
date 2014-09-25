package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.linear.BitUtils;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.multivariate.parameterization.ParameterizedPolynomialFunctions;

public class ParameterizedPolynomialFunctionTests {

    @Test
    public void testPpfSerialization() throws JsonProcessingException {
        SimplePolynomialFunction base = PolynomialFunctions.randomFunction(128, 128);
        SimplePolynomialFunction[] pipelines = { PolynomialFunctions.identity(128) };
        SimplePolynomialFunction parameterized = ParameterizedPolynomialFunctions.fromUnshiftedVariables(
                base.getInputLength(), base, pipelines);

        KodexObjectMapperFactory factory = new KodexObjectMapperFactory();
        ObjectMapper mapper = factory.getObjectMapper(null);
        String serializedPpf = mapper.writeValueAsString(parameterized);
        Assert.assertNotNull(serializedPpf);
    }

    @Test
    public void testPpfDeserialization() throws IOException {
        SimplePolynomialFunction base = PolynomialFunctions.randomFunction(128, 128);
        SimplePolynomialFunction[] pipelines = { PolynomialFunctions.identity(128) };
        SimplePolynomialFunction parameterized = ParameterizedPolynomialFunctions.fromUnshiftedVariables(
                base.getInputLength(), base, pipelines);

        KodexObjectMapperFactory factory = new KodexObjectMapperFactory();
        ObjectMapper mapper = factory.getObjectMapper(null);
        String serializedPpf = mapper.writeValueAsString(parameterized);

        SimplePolynomialFunction recovered = mapper.readValue(serializedPpf, SimplePolynomialFunction.class);
        BitVector input = BitUtils.randomVector(128);
        Assert.assertEquals(parameterized.apply(input), recovered.apply(input));
    }
}
