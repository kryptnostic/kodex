package com.kryptnostic.kodex.v1.serialization.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

public class KodexObjectMapperFactory {

    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new KodexModule());
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new AfterburnerModule());
        return mapper;
    }
}
