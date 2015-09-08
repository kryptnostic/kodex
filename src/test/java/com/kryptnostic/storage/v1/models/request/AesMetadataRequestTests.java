package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.storage.v1.models.IndexedMetadata;
import com.kryptnostic.utils.SecurityConfigurationTestUtils;
import com.kryptnostic.utils.TestKeyLoader;

@SuppressWarnings( "javadoc" )
public class AesMetadataRequestTests extends SecurityConfigurationTestUtils {

    private static final int INDEX_LENGTH = 256;

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException {

        byte[] key = new byte[ INDEX_LENGTH >>>3 ];
        new Random().nextBytes( key );
        String documentId = "TEST";
        Metadata metadatum = new Metadata( documentId, "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = data.encrypt( loader );

        String expected = serialize( new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, documentId ) ) ) );

        System.out.println( expected );

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // Ensure the key matches
        Assert.assertArrayEquals( key, meta.getKey() );
        // Ensure we decrypted the metadata successfully
        Assert.assertFalse( meta.getData().isEncrypted() );
        Assert.assertNull( meta.getData().getEncryptedClassName() );
        Assert.assertEquals( metadatum.getClass().getName(), meta.getData().getClassName() );
        Assert.assertEquals( metadatum, meta.getData().getData() );
    }

    @Test
    /**
     * Does implicit deserialization produces an Encryptable in an ENCRYPTED state in the ABSENCE of a private key?
     * @throws IOException
     */
    @Ignore
    // TODO: make decryption in cryptoService using wrong key fail fast by storing a hash of the blockCiphertext length
    // for comparison by cryptoService
    public void testImplicitDeserializationKeyless() throws SecurityConfigurationException, IOException,
            ClassNotFoundException {

        byte[] key = new byte[ INDEX_LENGTH >>>3 ];
        new Random().nextBytes( key );
        String documentId = "TEST";
        Metadata metadatum = new Metadata( documentId, "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = data.encrypt( loader );

        String expected = serialize( new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, documentId ) ) ) );

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // ensure nothing was decrypted
        Assert.assertArrayEquals( key, meta.getKey() );
        Assert.assertNull( meta.getData().getClassName() );
        Assert.assertNull( meta.getData().getData() );

        // and ensure nothing was screwed up in the ciphertext as a result of deserialization
        Assert.assertArrayEquals( data.getEncryptedData(), meta.getData().getEncryptedData() );
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
    @Ignore
    public void testSerializationWithImplicitEncryption() throws JsonGenerationException, JsonMappingException,
            IOException {
        byte[] key = new byte[ INDEX_LENGTH >>>3 ];
        new Random().nextBytes( key );
        Metadata metadatum = new Metadata( "TEST", "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, "test" ) ) );

        String actual = serialize( req );

        String expectedSubstring = "{\"metadata\":[{\"key\":" + wrapQuotes( Base64.encodeBase64String( key ) );

        // weak substring assertion that does not test ciphertext validity
        // ciphertext validity is covered in serializationDeserializationTest
        Assert.assertThat( actual, CoreMatchers.containsString( expectedSubstring ) );
    }

    @Test
    /**
     * Does serialization of an Encryptable work if you opt to encrypt it explicitly, rather than registering
     *  an FHE SecurityConfiguration?
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void testSerializationWithExplicitEncryption() throws ClassNotFoundException,
            SecurityConfigurationException, IOException, ExecutionException {
        byte[] key = new byte[ INDEX_LENGTH >>>3 ];
        new Random().nextBytes( key );
        Metadata metadatum = new Metadata( "TEST", "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption
        CryptoService crypto = new PasswordCryptoService( Cypher.AES_CTR_128, "crypto-test".toCharArray() );

        CryptoServiceLoader loader = new TestKeyLoader();

        loader.put( PasswordCryptoService.class.getCanonicalName(), crypto );

        data = data.encrypt( loader );

        // create the metadataRequest with our (ENCRYPTED) Encryptable
        String docId = "test";
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, docId ) ) );

        ObjectMapper unloadedMapper = KodexObjectMapperFactory.getObjectMapper();

        String out = serialize( req, unloadedMapper );
        MetadataRequest outReq = deserialize( out, MetadataRequest.class, unloadedMapper );

        Assert.assertEquals( serialize( req, unloadedMapper ), serialize( outReq, unloadedMapper ) );
    }
}
