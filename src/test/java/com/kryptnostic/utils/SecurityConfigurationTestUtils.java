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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;

import retrofit.client.Client;
import retrofit.client.OkClient;

import com.google.common.base.Preconditions;
import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.Keys;
import com.kryptnostic.kodex.v1.crypto.keys.Keystores;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.CorruptKodexException;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.linear.EnhancedBitMatrix.SingularMatrixException;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.multivariate.util.SimplePolynomialFunctions;
import com.kryptnostic.storage.v1.models.request.QueryHasherPairRequest;
import com.squareup.okhttp.OkHttpClient;

/**
 * Provides some utilities for setting up AES encryption
 * 
 * @author sinaiman
 *
 */
public class SecurityConfigurationTestUtils extends SerializationTestUtils {
    private static Kodex<String>    availableKodex;
    protected PasswordCryptoService crypto;
    protected KeyPair               pair;
    protected Kodex<String>         kodex;

    @Before
    public void resetSecurity() throws ExecutionException {
        this.crypto = new PasswordCryptoService( Cypher.AES_CTR_128, new BigInteger( 130, new SecureRandom() )
                .toString( 32 ).toCharArray() );
        this.loader.clear();
        this.loader.put( PasswordCryptoService.class.getCanonicalName(), crypto );
        generateRsaKeyPair();
    }

    private void generateRsaKeyPair() {
        try {
            pair = Keys.generateRsaKeyPair( 4096 );
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
    }

    protected void generateKodex( SimplePolynomialFunction globalHashFunction ) {
        try {
            this.kodex = new Kodex<String>( Cypher.RSA_OAEP_SHA1_1024, Cypher.AES_CBC_PKCS5_128, pair.getPublic() );
            kodex.unseal( pair.getPublic(), pair.getPrivate() );
            new FreshKodexLoader( globalHashFunction ).generateAllKeys( kodex );
        } catch (
                SecurityConfigurationException
                | KodexException
                | SealedKodexException
                | SingularMatrixException
                | IOException
                | CorruptKodexException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | SignatureException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected Kodex<String> getAnyKodex() {
        if ( SecurityConfigurationTestUtils.availableKodex == null ) {
            if ( this.kodex == null ) {
                generateKodex( SimplePolynomialFunctions.randomFunction( 128, 64 ) );
            }
            SecurityConfigurationTestUtils.availableKodex = kodex;
        }
        return SecurityConfigurationTestUtils.availableKodex;
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

    // FIXME:copied from freshkodexloader
    private static class FreshKodexLoader {

        private final SimplePolynomialFunction globalHashFunction;

        public FreshKodexLoader( SimplePolynomialFunction globalHashFunction ) {
            Preconditions.checkNotNull( globalHashFunction );
            this.globalHashFunction = globalHashFunction;
        }

        private void generateAllKeys( Kodex<String> kodex ) throws SealedKodexException, KodexException,
                SecurityConfigurationException, SingularMatrixException, IOException {
            com.kryptnostic.crypto.PrivateKey fhePrivateKey = getFhePrivateKey();
            com.kryptnostic.crypto.PublicKey fhePublicKey = getFhePublicKey( fhePrivateKey );

            kodex.setKeyWithClassAndJackson( com.kryptnostic.crypto.PrivateKey.class, fhePrivateKey );
            kodex.setKeyWithClassAndJackson( com.kryptnostic.crypto.PublicKey.class, fhePublicKey );

            EncryptedSearchPrivateKey encryptedSearchPrivateKey = getEncryptedSearchPrivateKey();
            QueryHasherPairRequest queryHasher = getQueryHasher( encryptedSearchPrivateKey, fhePrivateKey );

            kodex.setKeyWithClassAndJackson( EncryptedSearchPrivateKey.class, encryptedSearchPrivateKey );
            kodex.setKeyWithJackson(
                    QueryHasherPairRequest.class.getCanonicalName(),
                    queryHasher.computeChecksum(),
                    String.class );

        }

        private QueryHasherPairRequest getQueryHasher(
                EncryptedSearchPrivateKey encryptedSearchPrivateKey,
                com.kryptnostic.crypto.PrivateKey fhePrivateKey ) throws SingularMatrixException, IOException {
            Pair<SimplePolynomialFunction, SimplePolynomialFunction> pair = encryptedSearchPrivateKey
                    .getQueryHasherPair( globalHashFunction, fhePrivateKey );

            return new QueryHasherPairRequest( pair.getLeft(), pair.getRight() );
        }

        private EncryptedSearchPrivateKey getEncryptedSearchPrivateKey() throws SingularMatrixException {
            return new EncryptedSearchPrivateKey( (int) Math.sqrt( globalHashFunction.getOutputLength() ) );
        }

        private com.kryptnostic.crypto.PublicKey getFhePublicKey( com.kryptnostic.crypto.PrivateKey fhePrivateKey ) {
            return new com.kryptnostic.crypto.PublicKey( fhePrivateKey );
        }

        private com.kryptnostic.crypto.PrivateKey getFhePrivateKey() {
            return new com.kryptnostic.crypto.PrivateKey( 128, 64 );
        }
    }

}
