package com.kryptnostic.api.v1.serialization.jackson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SimplePolynomialFunctionTests {
    private static final int LEN = 256;
    private static ObjectMapper mapper;

    @BeforeClass
    public static void init() {
        mapper = new KodexObjectMapperFactory().getObjectMapper();
    }

    @Test
    public void serializeSpfTest() throws JsonGenerationException, JsonMappingException, IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SimplePolynomialFunction spf = PolynomialFunctions.randomFunction(LEN, LEN);
        mapper.writeValue(out, spf);

        Assert.assertEquals(TestUtil.wrapQuotes(PolynomialFunctions.marshalSimplePolynomialFunction(spf)), new String(
                out.toByteArray()));
    }

    @Test
    public void deserializeSpfTest() throws JsonParseException, JsonMappingException, IOException {
        SimplePolynomialFunction spf = PolynomialFunctions.randomFunction(LEN, LEN);
        String serialized = TestUtil.wrapQuotes(PolynomialFunctions.marshalSimplePolynomialFunction(spf));
        SimplePolynomialFunction out = mapper.readValue(serialized, SimplePolynomialFunction.class);

        Assert.assertEquals(spf, out);
    }

}
