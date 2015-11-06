package com.kryptnostic.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.junit.Before;

import retrofit.client.Client;
import retrofit.client.OkClient;

import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.Keys;
import com.kryptnostic.kodex.v1.crypto.keys.Keystores;
import com.squareup.okhttp.OkHttpClient;

/**
 * Provides some utilities for setting up AES encryption
 *
 * @author sinaiman
 *
 */
public class SecurityConfigurationTestUtils extends SerializationTestUtils {
    protected PasswordCryptoService passwordCryptoService;
    protected KeyPair               pair;

    @Before
    public void resetSecurity() throws ExecutionException {
        this.passwordCryptoService = new PasswordCryptoService(
                Cypher.AES_CTR_128,
                new BigInteger( 130, new SecureRandom() ).toString( 32 ).toCharArray() );
        this.loader.clear();
        this.loader.put( PasswordCryptoService.class.getCanonicalName(), passwordCryptoService );
        generateRsaKeyPair();
    }

    private void generateRsaKeyPair() {
        try {
            pair = Keys.generateRsaKeyPair( 4096 );
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
