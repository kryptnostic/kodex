package com.kryptnostic.kodex.v1.security;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.models.Encryptable;

public class SecurityConfigurationMapping {

    private final Map<Encryptable.EncryptionScheme, SecurityConfiguration<?, ?>> map;

    public SecurityConfigurationMapping() {
        map = Maps.newHashMap();
    }

    public SecurityConfigurationMapping add(Encryptable.EncryptionScheme scheme, Object publicKey, Object privateKey) {
        map.put(scheme, new SecurityConfiguration(publicKey, privateKey));
        return this;
    }

    public SecurityConfiguration<?, ?> get(Encryptable.EncryptionScheme scheme) {
        return map.get(scheme);
    }
}
