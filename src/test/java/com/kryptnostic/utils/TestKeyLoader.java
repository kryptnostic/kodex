package com.kryptnostic.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;

public class TestKeyLoader implements CryptoServiceLoader {
    private ConcurrentMap<String, CryptoService> services = Maps.newConcurrentMap();

    @Override
    public void put( String id, CryptoService service ) {
        services.put( id, service );
    }

    @Override
    public Optional<CryptoService> get( String id ) throws ExecutionException {
        CryptoService s = services.get( id );
        if ( s == null ) {
            try {
                s = new AesCryptoService( Cypher.AES_CTR_128 );
            } catch ( NoSuchAlgorithmException | InvalidAlgorithmParameterException e ) {
                throw new ExecutionException( e );
            }

            CryptoService maybe = services.putIfAbsent( id, s );
            if ( maybe != null ) {
                s = maybe;
            }
        }
        return Optional.of( s );
    }

    @Override
    public Map<String, CryptoService> getAll( Set<String> ids ) throws ExecutionException {
        return Maps.newHashMap( services );
    }

    public void clear() {
        services.clear();
    }
}
