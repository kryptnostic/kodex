package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.search.v1.models.request.SearchFunctionUploadRequest;

public class SearchFunctionUploadRequestTests extends BaseSerializationTest {
    private static final int LEN = 256;

    @Test
    public void serializeTest() throws JsonGenerationException, JsonMappingException, IOException {
        SimplePolynomialFunction function = PolynomialFunctions.randomFunction(LEN, LEN);
        SearchFunctionUploadRequest req = new SearchFunctionUploadRequest(function);

        String str = "{\"function\":\"" + PolynomialFunctions.marshalSimplePolynomialFunction(function) + "\"}";

        Assert.assertEquals(str, serialize(req));
    }

    @Test
    public void deserializeTest() throws JsonParseException, JsonMappingException, IOException {
        SimplePolynomialFunction function = PolynomialFunctions.randomFunction(LEN, LEN);
        SearchFunctionUploadRequest req = new SearchFunctionUploadRequest(function);

        String str = "{\"function\":\"" + PolynomialFunctions.marshalSimplePolynomialFunction(function) + "\"}";

        SearchFunctionUploadRequest out = deserialize(str, SearchFunctionUploadRequest.class);

        Assert.assertEquals(req.getFunction(), out.getFunction());
    }
}