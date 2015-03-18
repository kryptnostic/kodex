package com.kryptnostic.kodex.v1.serialization.jackson;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleKeyDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;
import com.kryptnostic.multivariate.gf2.Monomial;

@SuppressWarnings( "serial" )
public class KodexModule extends SimpleModule {
    // TODO: I'd like to read this version info from one place or not have it here at all
    public static final Version         KODEX_JACKSON_MODULE_VERSION = new Version(
                                                                             0,
                                                                             0,
                                                                             1,
                                                                             "SNAPSHOT",
                                                                             "com.kryptnostic",
                                                                             "kodex" );
    protected final CryptoServiceLoader loader;

    public KodexModule() {
        super( "KodexModule", KODEX_JACKSON_MODULE_VERSION );
        this.loader = null;
    }

    public KodexModule( CryptoServiceLoader loader ) {
        super( "KodexModule", KODEX_JACKSON_MODULE_VERSION );

        this.loader = loader;
    }

    @Override
    /**
     * All new Jackson de/serializers need to be added here
     */
    public void setupModule( SetupContext context ) {
        super.setupModule( context );

        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer( BitVector.class, new BitVectorSerializer() );
        serializers.addSerializer( Monomial.class, new MonomialSerializer() );
        serializers.addSerializer( Encryptable.class, new EncryptableSerializer( loader ) );

        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer( BitVector.class, new BitVectorDeserializer() );
        deserializers.addDeserializer( Monomial.class, new MonomialDeserializer() );

        context.addSerializers( serializers );
        context.addDeserializers( deserializers );

        SimpleSerializers keySerializers = new SimpleSerializers();
        keySerializers.addSerializer( BitVector.class, new BitVectorKeySerializer() );
        context.addKeySerializers( keySerializers );

        SimpleKeyDeserializers keyDeserializers = new SimpleKeyDeserializers();
        keyDeserializers.addDeserializer( BitVector.class, new BitVectorKeyDeserializer() );
        context.addKeyDeserializers( keyDeserializers );
    }
}
