package com.kryptnostic.kodex.v1.crypto.keys;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.kodex.v1.client.KryptnosticConnection;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class DefaultCryptoServiceLoader implements CryptoServiceLoader {
    /*
     * private static JacksonKodexMarshaller<PublicKey> publicKeyKodexFactory = new JacksonKodexMarshaller<PublicKey>(
     * PublicKey.class, mapper ); private static JacksonKodexMarshaller<PrivateKey> privateKeyKodexFactory = new
     * JacksonKodexMarshaller<PrivateKey>( PrivateKey.class, mapper );
     */

    private final LoadingCache<String, CryptoService> keyCache;
    private DirectoryApi                              directoryApi;
    private KryptnosticConnection                     connection;
    private Cypher                                    cypher;

    public DefaultCryptoServiceLoader(
            final KryptnosticConnection connection,
            final DirectoryApi directoryApi,
            Cypher cypher ) {
        this.directoryApi = directoryApi;
        this.connection = connection;
        this.cypher = cypher;
        keyCache = CacheBuilder.newBuilder().maximumSize( 1000 ).expireAfterWrite( 10, TimeUnit.MINUTES )
                .build( new CacheLoader<String, CryptoService>() {
                    @Override
                    public CryptoService load( String key ) throws IOException, SecurityConfigurationException {
                        return connection.getRsaCryptoService().decrypt(
                                directoryApi.getDocumentId( key ).getData(),
                                AesCryptoService.class );
                    }
                } );
    }

    @Override
    public CryptoService get( String id ) throws ExecutionException {
        CryptoService cs = keyCache.get( id );
        if ( cs == null ) {
            try {
                put( id, new AesCryptoService( this.cypher ) );
                cs = keyCache.get( id );
            } catch ( NoSuchAlgorithmException | InvalidAlgorithmParameterException e ) {
                throw new ExecutionException( e );
            }
        }
        return cs;
    }

    @Override
    public void put( String id, CryptoService service ) throws ExecutionException {
        keyCache.put( id, service );
        try {
            directoryApi.setDocumentId( id, connection.getRsaCryptoService().encrypt( service ) );
        } catch ( SecurityConfigurationException | IOException e ) {
            throw new ExecutionException( e );
        }
    }
}
