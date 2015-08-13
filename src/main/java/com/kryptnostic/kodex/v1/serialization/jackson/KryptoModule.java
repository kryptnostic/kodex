package com.kryptnostic.kodex.v1.serialization.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

public class KryptoModule extends SimpleModule {
    private static final long           serialVersionUID              = 1679451956984264772L;

    // TODO: I'd like to read this version info from one place or not have it here at all
    public static final Version         KRYPTO_JACKSON_MODULE_VERSION = new Version(
                                                                              0,
                                                                              0,
                                                                              1,
                                                                              "SNAPSHOT",
                                                                              "com.kryptnostic",
                                                                              "krypto" );
    protected final CryptoServiceLoader loader;

    public KryptoModule() {
        this( null );
    }

    public KryptoModule( CryptoServiceLoader loader ) {
        super( "KryptoModule", KRYPTO_JACKSON_MODULE_VERSION );
        this.loader = loader;
    }

    @Override
    public void setupModule( SetupContext context ) {
        super.setupModule( context );
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer( Encryptable.class, new EncryptableSerializer( Optional.of( loader ) ) );
        context.addSerializers( serializers );
    }
}
