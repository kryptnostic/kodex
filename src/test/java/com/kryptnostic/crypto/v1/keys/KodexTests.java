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

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.keys.Kodex.CorruptKodexException;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.linear.EnhancedBitMatrix.SingularMatrixException;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.multivariate.util.SimplePolynomialFunctions;
import com.kryptnostic.users.v1.UserKey;

public class KodexTests {
    private static KeyPair                   pair;
    private static KodexMarshaller<UserKey>  marshaller;
    private static PrivateKey                privateKey = new PrivateKey( 128, 64 );
    private static PublicKey                 publicKey  = new PublicKey( privateKey );
    private static EncryptedSearchPrivateKey searchKey;
    private static SimplePolynomialFunction  globalHash;

    @BeforeClass
    public static void generateKeys() throws NoSuchAlgorithmException, SingularMatrixException {
        pair = Keys.generateRsaKeyPair( 1024 );
        searchKey = new EncryptedSearchPrivateKey( 8 );
        marshaller = new JacksonKodexMarshaller<UserKey>( UserKey.class );
        globalHash = SimplePolynomialFunctions.denseRandomMultivariateQuadratic( 128, 64 );
    }

    @Test
    public void testSerializeDeserialize() throws InvalidKeyException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidParameterSpecException, SealedKodexException, IOException, InvalidAlgorithmParameterException,
            SignatureException, CorruptKodexException, SecurityConfigurationException, KodexException,
            SingularMatrixException {
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
        expected.verify( pair.getPublic() );

        expected.unseal( pair.getPrivate() );
        Assert.assertTrue( !expected.isSealed() );

        expected.setKeyWithJackson( PrivateKey.class.getCanonicalName(), privateKey, PrivateKey.class );
        expected.setKeyWithJackson( PublicKey.class.getCanonicalName(), publicKey, PublicKey.class );
        expected.setKeyWithJackson(
                EncryptedSearchPrivateKey.class.getCanonicalName(),
                searchKey,
                EncryptedSearchPrivateKey.class );
        Pair<SimplePolynomialFunction, SimplePolynomialFunction> queryPair = searchKey.getQueryHasherPair(
                globalHash,
                privateKey );
        SimplePolynomialFunction left = queryPair.getLeft();
        SimplePolynomialFunction right = queryPair.getRight();
        expected.setKeyWithJackson(
                SimplePolynomialFunction.class.getCanonicalName() + "LEFT",
                left,
                SimplePolynomialFunction.class );
        expected.setKeyWithJackson(
                SimplePolynomialFunction.class.getCanonicalName() + "RIGHT",
                right,
                SimplePolynomialFunction.class );

        ObjectMapper mapper = KodexObjectMapperFactory.getObjectMapper();

        String k = mapper.writeValueAsString( expected );
        Kodex<String> actual = mapper.readValue( k, new TypeReference<Kodex<String>>() {} );

        Assert.assertTrue( actual.isSealed() );
        actual.verify( pair.getPublic() );
        actual.unseal( pair.getPrivate() );
        actual.verify( pair.getPublic() );

        Assert.assertEquals( expected.getKey( "test", marshaller ), actual.getKey( "test", marshaller ) );
        Assert.assertEquals(
                privateKey,
                expected.getKeyWithJackson( PrivateKey.class.getCanonicalName(), PrivateKey.class ) );
        Assert.assertEquals(
                publicKey,
                expected.getKeyWithJackson( PublicKey.class.getCanonicalName(), PublicKey.class ) );

        EncryptedSearchPrivateKey recoveredSearchKey = expected.getKeyWithJackson(
                EncryptedSearchPrivateKey.class.getCanonicalName(),
                EncryptedSearchPrivateKey.class );

        Assert.assertEquals( searchKey.getLeftSquaringMatrix(), recoveredSearchKey.getLeftSquaringMatrix() );
        Assert.assertEquals( searchKey.getRightSquaringMatrix(), recoveredSearchKey.getRightSquaringMatrix() );
    }
}
