package com.kryptnostic.api.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kryptnostic.multivariate.PolynomialFunctions;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SimplePolynomialFunctionSerializer extends JsonSerializer<SimplePolynomialFunction> {

    @Override
    public void serialize(SimplePolynomialFunction value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String out = PolynomialFunctions.marshalSimplePolynomialFunction(value);
        jgen.writeString(out);
    }

}
