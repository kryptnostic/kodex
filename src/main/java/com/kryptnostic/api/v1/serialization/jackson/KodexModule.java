package com.kryptnostic.api.v1.serialization.jackson;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

@SuppressWarnings("serial")
public class KodexModule extends SimpleModule {
    // TODO: I'd like to read this version info from one place or not have it here at all
    public static final Version KODEX_JACKSON_MODULE_VERSION = new Version(0, 0, 1, "SNAPSHOT", "com.kryptnostic",
            "kodex");

    public KodexModule() {
        super("KodexModule", KODEX_JACKSON_MODULE_VERSION);
    }

    @Override
    /**
     * All new Jackson de/serializers need to be added here
     */
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(BitVector.class, new BitVectorSerializer());

        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(BitVector.class, new BitVectorDeserializer());

        context.addSerializers(serializers);
        context.addDeserializers(deserializers);
    }
}
