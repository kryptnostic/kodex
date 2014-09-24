package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kryptnostic.multivariate.Monomials;
import com.kryptnostic.multivariate.gf2.Monomial;

public class MonomialDeserializer extends JsonDeserializer<Monomial>{

    @Override
    public Monomial deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Monomials.unmarshallMonomial(jp.getValueAsString()); 
    }

}
