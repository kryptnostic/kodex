package com.kryptnostic.kodex.v1.serialization.jackson;

import com.fasterxml.jackson.databind.InjectableValues.Std;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.multivariate.polynomial.BasePolynomialFunction;
import com.kryptnostic.multivariate.polynomial.CompoundPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.OptimizedPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.ParameterizedPolynomialFunctionGF2;

public class KodexObjectMapperFactory {

    public ObjectMapper getObjectMapper(Kodex<String> kodex) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new KodexModule(kodex));
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new AfterburnerModule());
        mapper.registerSubtypes(CompoundPolynomialFunctionGF2.class, OptimizedPolynomialFunctionGF2.class,
                BasePolynomialFunction.class, ParameterizedPolynomialFunctionGF2.class);

        Std injectableValues = new Std();
        injectableValues.addValue(Kodex.class, kodex);
        mapper.setInjectableValues(injectableValues);

        return mapper;
    }
    
    public ObjectMapper getObjectMapper() {
        return getObjectMapper( null );
    }
}
