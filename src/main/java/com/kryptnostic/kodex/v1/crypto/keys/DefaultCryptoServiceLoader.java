package com.kryptnostic.kodex.v1.crypto.keys;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RetrofitError;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.directory.v1.model.ByteArrayEnvelope;
import com.kryptnostic.kodex.v1.client.KryptnosticConnection;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

public class DefaultCryptoServiceLoader implements CryptoServiceLoader {
    private static final Logger                       logger = LoggerFactory
                                                                     .getLogger( DefaultCryptoServiceLoader.class );
    /*
     * private static JacksonKodexMarshaller<PublicKey> publicKeyKodexFactory = new JacksonKodexMarshaller<PublicKey>(
     * PublicKey.class, mapper ); private static JacksonKodexMarshaller<PrivateKey> privateKeyKodexFactory = new
     * JacksonKodexMarshaller<PrivateKey>( PrivateKey.class, mapper );
     */

    private final LoadingCache<KeyLoadRequest, CryptoService> keyCache;
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
                .build( new CacheLoader<KeyLoadRequest, CryptoService>() {
                    @Override
                    public Map<KeyLoadRequest, CryptoService> loadAll( Iterable<? extends KeyLoadRequest> keys ) throws IOException,
                            SecurityConfigurationException {

                        Set<String> ids = Sets.newLinkedHashSet();
                        for ( KeyLoadRequest k : keys ) {
                            ids.add( k );
                        }

                        Map<String, byte[]> data = directoryApi.getObjectCryptoServices( ids );

                        Map<String, CryptoService> processedData = Maps.newHashMap();

                        for ( Map.Entry<String, byte[]> entry : data.entrySet() ) {
                            byte[] crypto = entry.getValue();
                            if ( crypto != null ) {
                                CryptoService service = connection.getRsaCryptoService().decrypt(
                                        crypto,
                                        AesCryptoService.class );
                                processedData.put( entry.getKey(), service );
                            }
                        }
                        return processedData;
                    }

                    @Override
                    public CryptoService load( KeyLoadRequest key ) throws IOException, SecurityConfigurationException {
                        byte[] crypto = null;
                        try {
                            crypto = directoryApi.getObjectCryptoService( key.getId() ).getData();
                        } catch ( ResourceNotFoundException e ) {} catch ( RetrofitError e ) {
                            throw new IOException( e );
                        }
                        if ( (crypto == null) && key.getOptions().contains( Options.CREATE_IF_NOT_EXISTS ) ) {
                            try {
                                CryptoService cs = new AesCryptoService( DefaultCryptoServiceLoader.this.cypher );
                                put( key, cs );
                                return cs;
                            } catch (
                                    NoSuchAlgorithmException
                                    | InvalidAlgorithmParameterException
                                    | ExecutionException e ) {

                            }
                        }
                        return connection.getRsaCryptoService().decrypt( crypto, AesCryptoService.class );
                    }
                } );
    }

    @Override
    public Optional<CryptoService> get( String id ) throws ExecutionException {
        return Optional.fromNullable( keyCache.get( id ) );
    }

    @Override
    public void put( KeyLoadRequest request, CryptoService service ) throws ExecutionException {
        keyCache.put( request.getId(), service );
        try {
            byte[] cs = connection.getRsaCryptoService().encrypt( service );
            directoryApi.setObjectCryptoService( request.getId(), new ByteArrayEnvelope( cs ) );
        } catch ( SecurityConfigurationException | IOException e ) {
            throw new ExecutionException( e );
        }
    }

    @Override
    public Map<String, CryptoService> getAll( Set<String> ids ) throws ExecutionException {
        Set<KeyLoadRequest> requests = Sets.newHashSetWithExpectedSize( ids.size() );
        for( String id : ids ) {
            requests.add( new KeyLoadRequest() )
        }
        return keyCache.getAll( ids );
    }

    @Override
    public void clear() {
        keyCache.cleanUp();
    }

    public static class KeyLoadRequest {
        private final Set<Options> options;
        private final String       id;

        public KeyLoadRequest( String id ) {
            this( id, EnumSet.of( Options.CREATE_IF_NOT_EXISTS ) );
        }

        public KeyLoadRequest( String id, Set<Options> options ) {
            this.id = id;
            this.options = options;
        }

        public Set<Options> getOptions() {
            return options;
        }

        public String getId() {
            return id;
        }
        
        public static KeyLoadRequest createIfNotExists( String id ) {
            
        }

        public static KeyLoadRequest forExisting( String id ) {
            return new KeyLoadRequest( id, EnumSet.of( Options.FAIL_IF_NOT_EXISTS ) );
        }
    }

    public static enum Options {
        CREATE_IF_NOT_EXISTS,
        FAIL_IF_NOT_EXISTS
    }
}
