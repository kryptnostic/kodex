package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import javax.annotation.Nonnull;

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
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.models.KryptnosticUser;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public final class KodexObjectMapperFactory {
    private final static ObjectMapper globalJsonMapper;
    private final static ObjectMapper globalSmileMapper;

    static {
        globalJsonMapper = newJsonMapper();
        configureNoCryptoOnSerialization( globalJsonMapper );

        globalSmileMapper = newSmileMapper();
        configureNoCryptoOnSerialization( globalSmileMapper );
    }

    private KodexObjectMapperFactory() {};

    /**
     * Returns a global regular json jackson object mapper capable of serializing {@link CompoundPolynomialFunctionGF2},
     * {@link SimplePolynomialFunction}, {@link BasePolynomialFunction}, {@link ParameterizedPolynomialFunctionGF2},
     * {@link ZeroPaddingStrategy}, {@link KryptnosticUser}, and {@link Encryptable}. This object mapper will not
     * decrypt on deserialization.
     * 
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return globalJsonMapper;
    }

    /**
     * Returns a global binary smile jackson object mapper capable of serializing {@link CompoundPolynomialFunctionGF2},
     * {@link SimplePolynomialFunction}, {@link BasePolynomialFunction}, {@link ParameterizedPolynomialFunctionGF2},
     * {@link ZeroPaddingStrategy}, {@link KryptnosticUser}, and {@link Encryptable}. This object mapper will not
     * decrypt on deserialization.
     * 
     * @return
     */
    public static ObjectMapper getSmileMapper() {
        return globalSmileMapper;
    }

    public static ObjectMapper getObjectMapper( @Nonnull CryptoServiceLoader loader ) {
        ObjectMapper mapper = newJsonMapper();
        configureCryptoOnSerialization(
                mapper,
                Preconditions.checkNotNull( loader, "CryptoServiceLoader cannot be null." ) );
        return mapper;
    }

    public static ObjectMapper getSmileMapper( @Nonnull CryptoServiceLoader loader ) {
        ObjectMapper mapper = newSmileMapper();
        configureCryptoOnSerialization(
                mapper,
                Preconditions.checkNotNull( loader, "CryptoServiceLoader cannot be null." ) );
        return mapper;
    }

    private static void configureNoCryptoOnSerialization( @Nonnull ObjectMapper mapper ) {
        Std injectableValues = new Std();
        injectableValues.addValue( CryptoServiceLoaderHolder.class, CryptoServiceLoaderHolder.getEmptyHolder() );
        mapper.setInjectableValues( injectableValues );
        mapper.registerModule( new KryptoModule() );
    }

    private static void configureCryptoOnSerialization(
            @Nonnull ObjectMapper mapper,
            @Nonnull CryptoServiceLoader loader ) {
        Std injectableValues = new Std();
        injectableValues.addValue( CryptoServiceLoaderHolder.class, CryptoServiceLoaderHolder.fromLoader( loader ) );
        mapper.setInjectableValues( injectableValues );
        mapper.registerModule( new KryptoModule( loader ) );
    }

    private static void configureMapper( @Nonnull ObjectMapper mapper ) {
        mapper.registerModule( new GuavaModule() );
        mapper.registerModule( new JodaModule() );
        mapper.registerModule( new AfterburnerModule() );
        mapper.registerSubtypes( KryptnosticUser.class );
    }

    private static ObjectMapper newJsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        configureMapper( mapper );
        return mapper;
    }

    private static ObjectMapper newSmileMapper() {
        ObjectMapper mapper = new ObjectMapper( new SmileFactory() );
        configureMapper( mapper );
        return mapper;
    }

    // TODO: Get rid of this
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
