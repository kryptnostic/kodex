package com.kryptnostic;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Maps;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.sharing.v1.models.DocumentId;

/**
 * Provides some utilities for setting up AES encryption
 * 
 * @author sinaiman
 *
 */
public class SecurityConfigurationTestUtils extends SerializationTestUtils {
    protected PasswordCryptoService crypto;
    protected KeyPair               pair;
    protected TestKeyLoader         loader;
    protected Kodex<String>         kodex;

    protected void initializeCryptoService() {
        resetSecurity();
        crypto = new PasswordCryptoService( Cypher.AES_CTR_PKCS5_128, new BigInteger( 130, new SecureRandom() )
                .toString( 32 ).toCharArray() );
        loader.register( PasswordCryptoService.class.getCanonicalName(), crypto );
    }

    protected void resetSecurity() {
        loader = new TestKeyLoader();
        mapper = KodexObjectMapperFactory.getObjectMapper( loader );
    }

    public static class TestKeyLoader implements CryptoServiceLoader {
        private Map<String, CryptoService> services = Maps.newHashMap();

        public void register( String id, CryptoService service ) {
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
}
