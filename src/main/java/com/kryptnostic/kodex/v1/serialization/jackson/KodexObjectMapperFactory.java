package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues.Std;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.kryptnostic.crypto.padding.ZeroPaddingStrategy;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.models.KryptnosticUser;
import com.kryptnostic.multivariate.polynomial.BasePolynomialFunction;
import com.kryptnostic.multivariate.polynomial.CompoundPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.OptimizedPolynomialFunctionGF2;
import com.kryptnostic.multivariate.polynomial.ParameterizedPolynomialFunctionGF2;

public final class KodexObjectMapperFactory {
    private static ObjectMapper globalMapper = null;

    private KodexObjectMapperFactory() {};

    public static void setGlobalObjectMapper( ObjectMapper mapper ) {
        // globalMapper = mapper;
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

    public static ObjectMapper getSmileMapper( CryptoServiceLoader loader ) {
        ObjectMapper mapper = new ObjectMapper( new SmileFactory() );
        configureMapper( mapper );
        configureMapperInjectables( mapper, loader );
        return mapper;
    }

    public static ObjectMapper getSmileMapper() {
        return getSmileMapper( null );
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
        mapper.registerModule( new JodaModule() );
        mapper.registerModule( new AfterburnerModule() );
        mapper.registerSubtypes(
                CompoundPolynomialFunctionGF2.class,
                OptimizedPolynomialFunctionGF2.class,
                BasePolynomialFunction.class,
                ParameterizedPolynomialFunctionGF2.class,
                ZeroPaddingStrategy.class,
                KryptnosticUser.class );
    }

    public static Converter getRetrofitConverter() {
        return new Converter() {
            private static final String MIME_TYPE    = "application/json; charset=UTF-8";
            ObjectMapper                objectMapper = getObjectMapper();

            @Override
            public TypedOutput toBody( Object object ) {
                try {
                    String json = objectMapper.writeValueAsString( object );
                    return new TypedByteArray( MIME_TYPE, json.getBytes( "UTF-8" ) );
                } catch ( JsonProcessingException e ) {
                    throw new AssertionError( e );
                } catch ( UnsupportedEncodingException e ) {
                    throw new AssertionError( e );
                }
            }

            @Override
            public Object fromBody( TypedInput body, Type type ) throws ConversionException {
                try {
                    JavaType javaType = objectMapper.getTypeFactory().constructType( type );
                    InputStream in = body.in();
                    if ( in.available() == 0 ) {
                        return null;
                    }
                    return objectMapper.readValue( body.in(), javaType );
                } catch ( JsonParseException e ) {
                    throw new ConversionException( e );
                } catch ( JsonMappingException e ) {
                    throw new ConversionException( e );
                } catch ( IOException e ) {
                    throw new ConversionException( e );
                }
            }
        };
    }
}
