package com.kryptnostic.utils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.sharing.v1.models.DocumentId;

public class TestKeyLoader implements CryptoServiceLoader {
    private Map<String, CryptoService> services = Maps.newHashMap();

    @Override
    public void put( String id, CryptoService service ) {
        services.put( id, service );
    }

    @Override
    public CryptoService get( DocumentId id ) throws ExecutionException {
        return services.get( id.getDocumentId() );
    }

    @Override
    public CryptoService get( String id ) throws ExecutionException {
        return services.get( id );
    }
}
