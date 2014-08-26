package com.kryptnostic.api.v1.serialization.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KodexObjectMapperFactory {

    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new KodexModule());
        return mapper;
    }
}
