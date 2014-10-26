package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.keys.Kodex.CorruptKodexException;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.users.v1.UserKey;

public class KodexTests {
    private static KeyPair                  pair;
    private static KodexMarshaller<UserKey> marshaller;
    private static PrivateKey               privateKey = new PrivateKey( 128, 64 );
    private static PublicKey                publicKey  = new PublicKey( privateKey );

    @BeforeClass
    public static void generateKeys() throws NoSuchAlgorithmException {
        pair = Keys.generateRsaKeyPair( 1024 );
        marshaller = new JacksonKodexMarshaller<UserKey>( UserKey.class );
    }

    @Test
    public void testSerializeDeserialize() throws InvalidKeyException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidParameterSpecException, SealedKodexException, IOException, InvalidAlgorithmParameterException,
            SignatureException, CorruptKodexException {
        Kodex<String> expected = new Kodex<String>(
                Cypher.RSA_OAEP_SHA1_1024,
                Cypher.AES_CTR_PKCS5_128,
                pair.getPublic() );
        Assert.assertTrue( expected.isSealed() );

        expected.unseal( pair.getPrivate() );
        Assert.assertTrue( !expected.isSealed() );

        expected.setKey( "test", marshaller, new UserKey( "kryptnostic", "tester" ) );
        expected.seal();

        Assert.assertTrue( expected.isSealed() );

        expected.unseal( pair.getPrivate() );
        Assert.assertTrue( !expected.isSealed() );

        expected.setKeyWithJackson( PrivateKey.class.getCanonicalName(), privateKey, PrivateKey.class );
        expected.setKeyWithJackson( PublicKey.class.getCanonicalName(), publicKey, PublicKey.class );

        ObjectMapper mapper = KodexObjectMapperFactory.getObjectMapper();

        String k = mapper.writeValueAsString( expected );
        Kodex<String> actual = mapper.readValue( k, new TypeReference<Kodex<String>>() {} );

        Assert.assertTrue( actual.isSealed() );
        actual.unseal( pair.getPrivate() );

        Assert.assertEquals( expected.getKey( "test", marshaller ), actual.getKey( "test", marshaller ) );
        Assert.assertEquals(
                privateKey,
                expected.getKeyWithJackson( PrivateKey.class.getCanonicalName(), PrivateKey.class ) );
        Assert.assertEquals(
                publicKey,
                expected.getKeyWithJackson( PublicKey.class.getCanonicalName(), PublicKey.class ) );
    }
}
