package com.kryptnostic.api.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SimplePolynomialFunctionDeserializer extends JsonDeserializer<SimplePolynomialFunction> {

    @Override
    public SimplePolynomialFunction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        return PolynomialFunctions.unmarshalSimplePolynomialFunction(jp.getValueAsString());
    }

}
