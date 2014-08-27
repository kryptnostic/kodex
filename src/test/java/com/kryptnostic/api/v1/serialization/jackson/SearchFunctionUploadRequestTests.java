package com.kryptnostic.api.v1.serialization.jackson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.search.v1.models.request.SearchFunctionUploadRequest;
import com.kryptnostic.search.v1.models.request.SearchRequest;

public class SearchFunctionUploadRequestTests {
    private static final int LEN = 256;
    private static ObjectMapper mapper;

    @BeforeClass
    public static void init() {
        mapper = new KodexObjectMapperFactory().getObjectMapper();
    }

    @Test
    public void serializeTest() throws JsonGenerationException, JsonMappingException, IOException {
        SimplePolynomialFunction function = PolynomialFunctions.randomFunction(LEN, LEN);
        SearchFunctionUploadRequest req = new SearchFunctionUploadRequest(function);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mapper.writeValue(out, req);

        String str = "{\"function\":\"" + PolynomialFunctions.marshalSimplePolynomialFunction(function) + "\"}";

        Assert.assertEquals(str, out.toString());
    }

    @Test
    public void deserializeTest() throws JsonParseException, JsonMappingException, IOException {
        SimplePolynomialFunction function = PolynomialFunctions.randomFunction(LEN, LEN);
        SearchFunctionUploadRequest req = new SearchFunctionUploadRequest(function);

        String str = "{\"function\":\"" + PolynomialFunctions.marshalSimplePolynomialFunction(function) + "\"}";

        SearchFunctionUploadRequest out = mapper.readValue(str, SearchFunctionUploadRequest.class);

        Assert.assertEquals(req.getFunction(), out.getFunction());
    }
}
