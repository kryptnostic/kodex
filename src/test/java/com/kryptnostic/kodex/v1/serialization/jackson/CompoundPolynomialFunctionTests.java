package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.multivariate.gf2.CompoundPolynomialFunction;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.multivariate.util.CompoundPolynomialFunctions;
import com.kryptnostic.multivariate.util.SimplePolynomialFunctions;

public class CompoundPolynomialFunctionTests {

    @Test
    public void testCpfSerialization() throws JsonProcessingException {
        SimplePolynomialFunction f = SimplePolynomialFunctions.lightRandomFunction( 128 , 512 );
        SimplePolynomialFunction g = SimplePolynomialFunctions.lightRandomFunction( 512 , 256 );
        SimplePolynomialFunction h = SimplePolynomialFunctions.lightRandomFunction( 256 , 64 );
        CompoundPolynomialFunction cpf = CompoundPolynomialFunctions.fromFunctions( f , g , h );

        KodexObjectMapperFactory factory = new KodexObjectMapperFactory();
        ObjectMapper mapper = factory.getObjectMapper(null);
        String serializedCpf = mapper.writeValueAsString(cpf);
        Assert.assertNotNull(serializedCpf);
    }
    
    @Test
    public void testCpfDeserialization() throws IOException {
        SimplePolynomialFunction f = SimplePolynomialFunctions.lightRandomFunction( 128 , 512 );
        SimplePolynomialFunction g = SimplePolynomialFunctions.lightRandomFunction( 512 , 256 );
        SimplePolynomialFunction h = SimplePolynomialFunctions.lightRandomFunction( 256 , 64 );
        CompoundPolynomialFunction cpf = CompoundPolynomialFunctions.fromFunctions( f , g , h );

        KodexObjectMapperFactory factory = new KodexObjectMapperFactory();
        ObjectMapper mapper = factory.getObjectMapper(null);
        String serializedCpf = mapper.writeValueAsString(cpf);
        
        CompoundPolynomialFunction recovered = mapper.readValue(serializedCpf, CompoundPolynomialFunction.class);
        BitVector input = BitVectors.randomVector(128);
        Assert.assertEquals(cpf.apply(input), recovered.apply(input));
    }
}
