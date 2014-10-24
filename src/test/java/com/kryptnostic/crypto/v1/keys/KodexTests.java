package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
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
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.users.v1.UserKey;


public class KodexTests {
    private static KeyPair                  pair;
    private static KodexMarshaller<UserKey> marshaller;

    @BeforeClass
    public static void generateKeys() throws NoSuchAlgorithmException {
        pair = Keys.generateRsaKeyPair( 1024 );
        marshaller = new JacksonKodexMarshaller<UserKey>( UserKey.class );
    }

    @Test
    public void testSerializeDeserialize() throws InvalidKeyException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidParameterSpecException, SealedKodexException, IOException, InvalidAlgorithmParameterException {
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

        ObjectMapper mapper = new ObjectMapper();
        String k = mapper.writeValueAsString( expected );
        Kodex<String> actual = mapper.readValue( k, new TypeReference<Kodex<String>>() {} );

        Assert.assertTrue( actual.isSealed() );
        actual.unseal( pair.getPrivate() );
        
        Assert.assertEquals( expected.getKey( "test", marshaller ),  actual.getKey( "test", marshaller ) );

    }
}
