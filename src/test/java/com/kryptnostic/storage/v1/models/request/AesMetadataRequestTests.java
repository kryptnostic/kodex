package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.PasswordCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;
import com.kryptnostic.sharing.v1.models.DocumentId;
import com.kryptnostic.storage.v1.models.IndexedMetadata;
import com.kryptnostic.utils.SecurityConfigurationTestUtils;
import com.kryptnostic.utils.TestKeyLoader;

@SuppressWarnings( "javadoc" )
public class AesMetadataRequestTests extends SecurityConfigurationTestUtils {
    private static final int INDEX_LENGTH = 256;

    @Rule
    public ExpectedException exception    = ExpectedException.none();

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException {
        initializeCryptoService();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        DocumentId documentId = new DocumentId( "TEST" );
        Metadata metadatum = new Metadata( documentId, "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = data.encrypt( loader );

        String expected = serialize( new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, documentId ) ) ) );

        System.out.println( expected );

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // Ensure the key matches
        Assert.assertEquals( key, meta.getKey() );
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
        initializeCryptoService();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        DocumentId documentId = new DocumentId( "TEST" );
        Metadata metadatum = new Metadata( documentId, "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption to generate some json
        data = data.encrypt( loader );

        String expected = serialize( new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, documentId ) ) ) );

        // kill private key!
        initializeCryptoService();

        MetadataRequest deserialized = deserialize( expected, MetadataRequest.class );

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // ensure nothing was decrypted
        Assert.assertEquals( key, meta.getKey() );
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
    public void testSerializationWithImplicitEncryption() throws JsonGenerationException, JsonMappingException,
            IOException {
        initializeCryptoService();

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST" ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, new DocumentId(
                "test" ) ) ) );

        String actual = serialize( req );

        String expectedSubstring = "{\"metadata\":[{\"key\":" + wrapQuotes( BitVectors.marshalBitvector( key ) );

        // weak substring assertion that does not test ciphertext validity
        // ciphertext validity is covered in serializationDeserializationTest
        Assert.assertThat( actual, CoreMatchers.containsString( expectedSubstring ) );
    }

    /**
     * Does serialization of an Encryptable properly throw an exception if you don't properly register public/private
     * keys?
     * 
     * @throws IOException
     * @throws JsonGenerationException
     * 
     */
    @Test
    public void testSerializationWithImplicitEncryptionKeyless() throws JsonGenerationException, IOException {
        initializeCryptoService();

        loader.put( PasswordCryptoService.class.getCanonicalName(), null );

        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST" ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, new DocumentId(
                "test" ) ) ) );

        boolean caught = false;
        try {
            serialize( req );
        } catch ( Exception e ) {
            e.printStackTrace();
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
    public void testSerializationWithExplicitEncryption() throws ClassNotFoundException,
            SecurityConfigurationException, IOException, ExecutionException {
        BitVector key = BitVectors.randomVector( INDEX_LENGTH );
        Metadata metadatum = new Metadata( new DocumentId( "TEST" ), "test", Arrays.asList( 1, 2, 3 ) );
        Encryptable<Metadata> data = new Encryptable<Metadata>( metadatum );

        // explicit encryption
        CryptoService crypto = new PasswordCryptoService( Cypher.AES_CTR_128, "crypto-test".toCharArray() );

        CryptoServiceLoader loader = new TestKeyLoader();

        loader.put( PasswordCryptoService.class.getCanonicalName(), crypto );

        data = data.encrypt( loader );

        // create the metadataRequest with our (ENCRYPTED) Encryptable
        DocumentId docId = new DocumentId( "test" );
        MetadataRequest req = new MetadataRequest( Arrays.asList( new IndexedMetadata( key, data, docId ) ) );

        String out = serialize( req );
        MetadataRequest outReq = deserialize( out, MetadataRequest.class );

        Assert.assertEquals( serialize( req ), serialize( outReq ) );
    }
}
