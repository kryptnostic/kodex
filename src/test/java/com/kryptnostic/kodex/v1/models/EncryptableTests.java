package com.kryptnostic.kodex.v1.models;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.keys.JacksonKodexMarshaller;
import com.kryptnostic.crypto.v1.keys.Keys;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.crypto.v1.keys.Kodex.CorruptKodexException;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.KodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class EncryptableTests {

    private static final int     PRIVATE_KEY_BLOCK_SIZE = 64;
    private static final UserKey user                   = new UserKey( "kryptnostic", "tester" );
    private Kodex<String>        kodex;
    private KeyPair              pair;

    @Before
    public void init() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException,
            SealedKodexException, IOException, InvalidAlgorithmParameterException, SignatureException,
            CorruptKodexException, SecurityConfigurationException, KodexException {
        ObjectMapper mapper = KodexObjectMapperFactory.getObjectMapper();
        pair = Keys.generateRsaKeyPair( 1024 );
        kodex = new Kodex<String>( Cypher.RSA_OAEP_SHA1_1024, Cypher.AES_CTR_PKCS5_128, pair.getPublic() );

        PrivateKey privateKey = new PrivateKey( PRIVATE_KEY_BLOCK_SIZE * 2, PRIVATE_KEY_BLOCK_SIZE );
        PublicKey publicKey = new PublicKey( privateKey );

        kodex.unseal( pair.getPublic(), pair.getPrivate() );
        kodex.setKey( PrivateKey.class.getCanonicalName(), new JacksonKodexMarshaller<PrivateKey>(
                PrivateKey.class,
                mapper ), privateKey );
        kodex.setKey( PublicKey.class.getCanonicalName(), new JacksonKodexMarshaller<PublicKey>(
                PublicKey.class,
                mapper ), publicKey );
    }

    @Test
    public void encryptableStringConstructionTest() {
        String plain = "I am cool";
        Encryptable<String> plainString = new FheEncryptable<String>( plain );

        Assert.assertNull( plainString.getEncryptedData() );
        Assert.assertNull( plainString.getEncryptedClassName() );
        Assert.assertFalse( plainString.isEncrypted() );
        Assert.assertEquals( plain, plainString.getData() );
    }

    @Test
    public void encryptableStringEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException, SecurityConfigurationException {
        String plain = "I am cool";
        Encryptable<String> plainString = new FheEncryptable<String>( plain );

        Encryptable<String> cipherString = plainString.encrypt( kodex );

        Assert.assertNull( plainString.getEncryptedData() );
        Assert.assertNull( plainString.getEncryptedClassName() );
        Assert.assertFalse( plainString.isEncrypted() );
        Assert.assertEquals( plain, plainString.getData() );

        Assert.assertNotNull( cipherString.getEncryptedData() );
        Assert.assertNotNull( cipherString.getEncryptedClassName() );
        Assert.assertTrue( cipherString.isEncrypted() );
        Assert.assertNull( cipherString.getData() );

        Encryptable<String> decryptedString = cipherString.decrypt( kodex );
        Assert.assertNull( decryptedString.getEncryptedData() );
        Assert.assertNull( decryptedString.getEncryptedClassName() );
        Assert.assertFalse( decryptedString.isEncrypted() );
        Assert.assertEquals( plain, decryptedString.getData() );
    }

    @Test
    public void encryptableMetadataConstructionTest() {
        Metadata m = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> plainString = new FheEncryptable<Metadata>( m );

        Assert.assertNull( plainString.getEncryptedData() );
        Assert.assertNull( plainString.getEncryptedClassName() );
        Assert.assertFalse( plainString.isEncrypted() );
        Assert.assertEquals( m, plainString.getData() );
    }

    @Test
    public void encryptableMetadataEncryptionTest() throws JsonParseException, JsonMappingException, IOException,
            ClassNotFoundException, SecurityConfigurationException {
        Metadata m = new Metadata( new DocumentId( "ABC", user ), "ABC", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> plainString = new FheEncryptable<Metadata>( m );
        Encryptable<Metadata> cipherString = plainString.encrypt( kodex );

        Assert.assertNull( plainString.getEncryptedData() );
        Assert.assertNull( plainString.getEncryptedClassName() );
        Assert.assertFalse( plainString.isEncrypted() );
        Assert.assertEquals( m, plainString.getData() );

        Assert.assertNotNull( cipherString.getEncryptedData() );
        Assert.assertNotNull( cipherString.getEncryptedClassName() );
        Assert.assertTrue( cipherString.isEncrypted() );
        Assert.assertNull( cipherString.getData() );

        Encryptable<Metadata> decryptedString = cipherString.decrypt( kodex );

        Assert.assertNull( decryptedString.getEncryptedData() );
        Assert.assertNull( decryptedString.getEncryptedClassName() );
        Assert.assertFalse( decryptedString.isEncrypted() );
        Assert.assertEquals( m, decryptedString.getData() );
    }

}
