package com.kryptnostic.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;

public class TestKeyLoader implements CryptoServiceLoader {
    private Map<String, CryptoService> services = Maps.newHashMap();

    @Override
    public void put( String id, CryptoService service ) {
        services.put( id, service );
    }

    @Override
    public CryptoService get( String id ) throws ExecutionException {
        CryptoService s = services.get( id );
        if ( s == null ) {
            try {
                s = new AesCryptoService( Cypher.AES_CTR_128 );
            } catch ( NoSuchAlgorithmException | InvalidAlgorithmParameterException e ) {
                throw new ExecutionException( e );
            }
            services.put( id, s );
        }
        return s;
    }
}
