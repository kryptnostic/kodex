package com.kryptnostic.api.v1.serialization.jackson;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class KodexObjectMapperFactory {

    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BitVector.class, new BitVectorDeserializer());
        module.addSerializer(BitVector.class, new BitVectorSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}
