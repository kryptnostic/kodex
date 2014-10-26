package com.kryptnostic.storage.v1.models.request;

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

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.crypto.v1.keys.JacksonKodexMarshaller;
import com.kryptnostic.crypto.v1.keys.Keys;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.crypto.v1.keys.Kodex.CorruptKodexException;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

public class AesMetadataRequestTests extends AesEncryptableBase {
    private static final UserKey user         = new UserKey( "kryptnostic", "tester" );
    private static final int     INDEX_LENGTH = 256;

    @Rule
    public ExpectedException     exception    = ExpectedException.none();

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws IOException, SecurityConfigurationException, InvalidKeyException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException,
            SealedKodexException, SignatureException, CorruptKodexException {
        initImplicitEncryption();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST", user ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new AesEncryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = (AesEncryptable<Metadata>) data.encrypt( this.kodex );


        String encryptedData = serialize( data.getEncryptedData() );
        String encryptedClassName = serialize( data.getEncryptedClassName() );

        System.out.println( encryptedData );
        System.out.println( encryptedClassName );

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes( BitVectors.marshalBitvector( key ) )
                + ",\"data\":{\"" + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + ",\"" + Encryptable.FIELD_CLASS
                + "\":\"" + data.getClass().getCanonicalName() + "\"}}]}";

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // Ensure the key matches
        Assert.assertEquals( key, meta.getKey() );
        // Ensure we decrypted the metadata successfully
        Assert.assertNull( meta.getData().getEncryptedClassName() );
        Assert.assertEquals( metadatum.getClass().getName(), meta.getData().getClassName() );
        Assert.assertEquals( metadatum, meta.getData().getData() );
    }

    @Test
    /**
     * Does implicit deserialization produces an Encryptable in an ENCRYPTED state in the ABSENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserializationKeyless() throws IOException, SecurityConfigurationException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, CorruptKodexException {
        initImplicitEncryption();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST", user ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new AesEncryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = data.encrypt( kodex );

        String encryptedData = serialize( data.getEncryptedData() );
        String encryptedClassName = serialize( data.getEncryptedClassName() );

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes( BitVectors.marshalBitvector( key ) )
                + ",\"data\":{\"" + Encryptable.FIELD_CLASS + "\":\"" + data.getClass().getCanonicalName() + "\",\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + "}}]}";

        // kill private key!
        resetSecurityConfiguration();

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // ensure nothing was decrypted
        Assert.assertEquals( key, meta.getKey() );
        Assert.assertNull( meta.getData().getClassName() );
        Assert.assertNull( meta.getData().getData() );

        // and ensure nothing was screwed up in the ciphertext as a result of deserialization
        Assert.assertArrayEquals( data.getEncryptedData().getContents(), meta.getData().getEncryptedData()
                .getContents() );
        Assert.assertArrayEquals( data.getEncryptedData().getLength(), meta.getData().getEncryptedData().getLength() );
        Assert.assertArrayEquals( data.getEncryptedClassName().getContents(), meta.getData().getEncryptedClassName()
                .getContents() );
        Assert.assertArrayEquals( data.getEncryptedClassName().getLength(), meta.getData().getEncryptedClassName()
                .getLength() );
    }

    @Test
    /**
     * Does serialization of Encryptable work if you create an objectMapper with an FHE SecurityConfiguration?
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void testSerializationWithImplicitEncryption() throws JsonGenerationException, JsonMappingException,
            IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, CorruptKodexException {
        initImplicitEncryption();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST", user ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new AesEncryptable<Metadata>( metadatum );

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data ) ) );

        String actual = serialize( req );

        String expectedSubstring = "{\"metadata\":[{\"key\":" + wrapQuotes( BitVectors.marshalBitvector( key ) );

        String expectedTypeSubstring = "\"@class\":\"" + data.getClass().getCanonicalName() + "\"";

        // weak substring assertion that does not test ciphertext validity
        // ciphertext validity is covered in serializationDeserializationTest
        Assert.assertThat( actual, CoreMatchers.containsString( expectedSubstring ) );
        Assert.assertThat( actual, CoreMatchers.containsString( expectedTypeSubstring ) );
    }

    /**
     * Does serialization of an Encryptable properly throw an exception if you don't properly register public/private
     * keys?
     * 
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SealedKodexException
     * @throws InvalidParameterSpecException
     * @throws InvalidKeySpecException
     * @throws SignatureException 
     * @throws CorruptKodexException 
     */
    @Test
    public void testSerializationWithImplicitEncryptionKeyless() throws JsonGenerationException, JsonMappingException,
            IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, CorruptKodexException {
        resetSecurityConfiguration();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST", user ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new AesEncryptable<Metadata>( metadatum );

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data ) ) );

        boolean caught = false;
        try {
            serialize( req );
        } catch ( JsonMappingException e ) {
            if ( e.getCause() instanceof NullPointerException ) {
                caught = true;
            }
        }
        Assert.assertTrue( caught );
    }

    @Test
    /**
     * Does serialization of an Encryptable work if you opt to encrypt it explicitly, rather than registering
     *  an FHE SecurityConfiguration?
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void testSerializationWithExplicitEncryption() throws JsonGenerationException, JsonMappingException,
            IOException, SecurityConfigurationException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
            InvalidKeySpecException, InvalidParameterSpecException, SealedKodexException, SignatureException, CorruptKodexException {
        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST", user ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new AesEncryptable<Metadata>( metadatum );

        // explicit encryption
        CryptoService crypto = new CryptoService( Cypher.AES_CTR_PKCS5_128, "crypto-test".toCharArray() );

        KeyPair tmpPair = Keys.generateRsaKeyPair( 1024 );
        Kodex<String> tmpKodex = new Kodex<String>(
                Cypher.RSA_OAEP_SHA1_1024,
                Cypher.AES_CTR_PKCS5_128,
                tmpPair.getPublic() );
        tmpKodex.unseal( tmpPair.getPrivate() );
        tmpKodex.setKey( CryptoService.class.getCanonicalName(), new JacksonKodexMarshaller<CryptoService>(
                CryptoService.class ), crypto );

        data = (AesEncryptable<Metadata>) data.encrypt( tmpKodex );

        // create the metadataRequest with our (ENCRYPTED) Encryptable
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data ) ) );

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes( BitVectors.marshalBitvector( key ) )
                + ",\"data\":{\"" + Encryptable.FIELD_CLASS + "\":\"" + data.getClass().getCanonicalName() + "\",\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + serialize( data.getEncryptedData() ) + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + serialize( data.getEncryptedClassName() ) + "}}]}";

        String actual = serialize( req );

        Assert.assertEquals( expected, actual );
    }
}
