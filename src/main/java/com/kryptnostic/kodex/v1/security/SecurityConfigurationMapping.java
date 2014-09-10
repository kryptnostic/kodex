package com.kryptnostic.kodex.v1.security;

import java.util.Map;

import com.google.common.collect.Maps;

public class SecurityConfigurationMapping {

    private final Map<Class, SecurityConfiguration<?, ?>> map;

    public SecurityConfigurationMapping() {
        map = Maps.newHashMap();
    }

    public SecurityConfigurationMapping add(Class scheme, Object publicKey, Object privateKey) {
        map.put(scheme, new SecurityConfiguration(publicKey, privateKey));
        return this;
    }

    public SecurityConfiguration<?, ?> get(Class scheme) {
        return map.get(scheme);
    }
}
