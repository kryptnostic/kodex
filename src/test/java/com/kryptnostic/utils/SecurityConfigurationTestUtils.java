package com.kryptnostic.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import retrofit.client.Client;
import retrofit.client.OkClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.Keys;
import com.kryptnostic.kodex.v1.crypto.keys.Keystores;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.CorruptKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.squareup.okhttp.OkHttpClient;

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
        crypto = new PasswordCryptoService( Cypher.AES_CTR_128, new BigInteger( 130, new SecureRandom() ).toString( 32 )
                .toCharArray() );
        loader.put( PasswordCryptoService.class.getCanonicalName(), crypto );
    }

    private void resetSecurity() {
        loader = new TestKeyLoader();
        mapper = KodexObjectMapperFactory.getObjectMapper( loader );

        generateRsaKeyPair();
        generateKodex();
    }

    private void generateKodex() {
        try {
            this.kodex = new Kodex<String>( Cypher.RSA_OAEP_SHA1_1024, Cypher.AES_CTR_128, pair.getPublic() );
            this.kodex.unseal( pair.getPublic(), pair.getPrivate() );
        } catch (
                InvalidKeyException
                | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | SignatureException
                | JsonProcessingException
                | SecurityConfigurationException
                | KodexException
                | CorruptKodexException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void generateRsaKeyPair() {
        try {
            pair = Keys.generateRsaKeyPair( 1024 );
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
    }

    protected Client createHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout( 0, TimeUnit.MILLISECONDS );
        client.setConnectTimeout( 0, TimeUnit.MILLISECONDS );
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance( "TLS" );
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance( TrustManagerFactory
                    .getDefaultAlgorithm() );
            trustManagerFactory.init( Keystores.loadKeystoreFromResource(
                    "security/rhizome.jks",
                    "rhizome".toCharArray() ) );
            sslContext.init( null, trustManagerFactory.getTrustManagers(), null );

            client.setSslSocketFactory( sslContext.getSocketFactory() );
        } catch (
                NoSuchAlgorithmException
                | KeyManagementException
                | KeyStoreException
                | CertificateException
                | IOException e ) {
            e.printStackTrace();
        }
        return new OkClient( client );
    }

}
