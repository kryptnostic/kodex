package com.kryptnostic.kodex.v1.serialization.jackson;

import com.fasterxml.jackson.databind.InjectableValues.Std;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.kryptnostic.crypto.padding.ZeroPaddingStrategy;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.multivariate.polynomial.BasePolynomialFunction;
import com.kryptnostic.multivariate.polynomial.CompoundPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.OptimizedPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.ParameterizedPolynomialFunctionGF2;

public final class KodexObjectMapperFactory {
    private static ObjectMapper globalMapper = null;

    private KodexObjectMapperFactory() {};

    public static void setGlobalObjectMapper( ObjectMapper mapper ) {
        globalMapper = mapper;
    }

    public static ObjectMapper getObjectMapper( CryptoServiceLoader loader ) {
        ObjectMapper mapper = getBaseMapper();
        configureMapperInjectables( mapper, loader );
        return mapper;
    }

    public static ObjectMapper getObjectMapper() {
        if ( globalMapper != null ) {
            return globalMapper;
        }
        return getObjectMapper( null );
    }

    public static ObjectMapper getSmileMapper() {
        ObjectMapper mapper = new ObjectMapper( new SmileFactory() );
        configureMapper( mapper );
        configureMapperInjectables( mapper, null );
        return mapper;
    }

    private static ObjectMapper getBaseMapper() {
        ObjectMapper mapper = new ObjectMapper();
        configureMapper( mapper );
        return mapper;
    }

    private static void configureMapperInjectables( ObjectMapper mapper, CryptoServiceLoader loader ) {
        Std injectableValues = new Std();
        injectableValues.addValue( CryptoServiceLoader.class, loader );
        mapper.setInjectableValues( injectableValues );
        mapper.registerModule( new KodexModule( loader ) );
    }

    private static void configureMapper( ObjectMapper mapper ) {
        mapper.registerModule( new KodexModule() );
        mapper.registerModule( new GuavaModule() );
        mapper.registerModule( new AfterburnerModule() );
        mapper.registerSubtypes(
                CompoundPolynomialFunctionGF2.class,
                OptimizedPolynomialFunctionGF2.class,
                BasePolynomialFunction.class,
                ParameterizedPolynomialFunctionGF2.class,
                ZeroPaddingStrategy.class );
    }
}
