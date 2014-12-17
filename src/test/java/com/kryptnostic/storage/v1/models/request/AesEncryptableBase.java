package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.google.common.collect.Maps;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.JacksonKodexMarshaller;
import com.kryptnostic.kodex.v1.crypto.keys.Keys;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.CorruptKodexException;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.sharing.v1.models.DocumentId;

/**
 * Provides some utilities for setting up AES encryption
 * 
 * @author sinaiman
 *
 */
public class AesEncryptableBase extends BaseSerializationTest {
    protected PasswordCryptoService crypto;
    protected KeyPair               pair;
    protected TestKeyLoader         loader = new TestKeyLoader();
    protected Kodex<String>         kodex;

    protected void initImplicitEncryption() throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
            InvalidKeySpecException, InvalidParameterSpecException, SealedKodexException, IOException,
            SignatureException, CorruptKodexException, KodexException, SecurityConfigurationException {
        resetSecurityConfiguration();
        // register key with object mapper
        this.kodex.unseal( pair.getPublic(), pair.getPrivate() );
        this.kodex.setKey(
                PasswordCryptoService.class.getCanonicalName(),
                new JacksonKodexMarshaller<PasswordCryptoService>( PasswordCryptoService.class ),
                crypto );
    }

    protected void initFheEncryption() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException,
            SealedKodexException, IOException, SignatureException, CorruptKodexException, KodexException,
            SecurityConfigurationException {
        PrivateKey privateKey = new PrivateKey( 128, 64 );
        PublicKey publicKey = new PublicKey( privateKey );
        //
        kodex.unseal( pair.getPublic(), pair.getPrivate() );
        kodex.setKey( PrivateKey.class.getCanonicalName(), new JacksonKodexMarshaller<PrivateKey>(
                PrivateKey.class,
                mapper ), privateKey );
        kodex.setKey( PublicKey.class.getCanonicalName(), new JacksonKodexMarshaller<PublicKey>(
                PublicKey.class,
                mapper ), publicKey );
    }

    protected void resetSecurityConfiguration() throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
            InvalidKeySpecException, InvalidParameterSpecException, SealedKodexException, IOException,
            SignatureException, CorruptKodexException, SecurityConfigurationException, KodexException {

        this.pair = Keys.generateRsaKeyPair( 1024 );
//        this.kodex = new Kodex<String>( Cypher.RSA_OAEP_SHA1_1024, Cypher.AES_CTR_128, pair.getPublic() );
//        this.kodex.unseal( pair.getPublic(), pair.getPrivate() );
        this.mapper = KodexObjectMapperFactory.getObjectMapper( loader );
        this.crypto = new PasswordCryptoService( Cypher.AES_CTR_128, new BigInteger( 130, new SecureRandom() ).toString(
                32 ).toCharArray() );
        loader.register( PasswordCryptoService.class.getCanonicalName() , crypto );
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
