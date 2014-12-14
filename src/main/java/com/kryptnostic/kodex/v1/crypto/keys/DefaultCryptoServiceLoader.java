package com.kryptnostic.kodex.v1.crypto.keys;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.kodex.v1.client.KryptnosticContext;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.sharing.v1.models.DocumentId;

public class DefaultCryptoServiceLoader implements CryptoServiceLoader {
    private final LoadingCache<String, CryptoService> keyCache;

    public DefaultCryptoServiceLoader( final KryptnosticContext context, final DirectoryApi directoryApi ) {
        keyCache = CacheBuilder.newBuilder().maximumSize( 1000 ).expireAfterWrite( 10, TimeUnit.MINUTES )
                .build( new CacheLoader<String, CryptoService>() {
                    public CryptoService load( String key ) throws IOException, SecurityConfigurationException {
                        return context.getRsaCryptoService().decrypt(
                                directoryApi.getDocumentId( key ).getData(),
                                AesCryptoService.class );
                    }
                } );
    }

    @Override
    public CryptoService get( DocumentId id ) throws ExecutionException {
        return keyCache.get( id.getDocumentId() );
    }

    @Override
    public CryptoService get( String id ) throws ExecutionException {
        return keyCache.get( id );
    }

}
