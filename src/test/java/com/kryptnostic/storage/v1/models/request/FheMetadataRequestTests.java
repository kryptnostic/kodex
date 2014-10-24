package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.models.FheEncryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.sharing.v1.DocumentId;
import com.kryptnostic.users.v1.UserKey;

/**
 * @TODO
 * 
 *       1) Write unit tests for FheEncryptable 2) Write unit tests for AesEncryptable 3) Remove FheMetadataRequestTests
 *       as it's irrelevant since no metadata will use FHE 4) Rename AesMetadataRequestTests to MetadataRequestTests 5)
 *       Drop tests from MetadataRequestTests that are covered by AesEncryptable unit tests
 */

public class FheMetadataRequestTests extends BaseSerializationTest {
    private static final UserKey user = new UserKey("kryptnostic","tester");
    private static final int INDEX_LENGTH = 256;

    private PrivateKey privKey;
    private PublicKey pubKey;
    private SecurityConfigurationMapping config;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private void initImplicitEncryption() {
        // register key with object mapper
        this.privKey = new PrivateKey(128, 64);
        this.pubKey = new PublicKey(privKey);

        resetSecurityConfiguration();
    }

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws IOException, SecurityConfigurationException {
        initImplicitEncryption();

        BitVector key = BitVectors.randomVector(INDEX_LENGTH);
        Metadata metadatum = new Metadata(new DocumentId("TEST",user), "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> data = new FheEncryptable<Metadata>(metadatum);

        // explicit encryption to generate some json
        data = (FheEncryptable<Metadata>) data.encrypt(this.config);

        String encryptedData = serialize(data.getEncryptedData());
        String encryptedClassName = serialize(data.getEncryptedClassName());

        System.out.println(encryptedData);
        System.out.println(encryptedClassName);

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + ",\"" + Encryptable.FIELD_CLASS
                + "\":\"" + data.getClass().getCanonicalName() + "\"}}]}";

        MetadataRequest deserialized = deserialize(expected, MetadataRequest.class);

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // Ensure the key matches
        Assert.assertEquals(key, meta.getKey());
        // Ensure we decrypted the metadata successfully
        Assert.assertNull(meta.getData().getEncryptedClassName());
        Assert.assertEquals(metadatum.getClass().getName(), meta.getData().getClassName());
        Assert.assertEquals(metadatum, meta.getData().getData());
    }

    @Test
    /**
     * Does implicit deserialization produces an Encryptable in an ENCRYPTED state in the ABSENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserializationKeyless() throws IOException, SecurityConfigurationException {
        initImplicitEncryption();

        // kill private key!
        this.privKey = null;
        resetSecurityConfiguration();

        BitVector key = BitVectors.randomVector(INDEX_LENGTH);
        Metadata metadatum = new Metadata(new DocumentId("TEST",user), "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> data = new FheEncryptable<Metadata>(metadatum);

        // explicit encryption to generate some json
        data = data.encrypt(config);

        String encryptedData = serialize(data.getEncryptedData());
        String encryptedClassName = serialize(data.getEncryptedClassName());

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_CLASS + "\":\"" + data.getClass().getCanonicalName() + "\",\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + "}}]}";

        MetadataRequest deserialized = deserialize(expected, MetadataRequest.class);

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        // ensure nothing was decrypted
        Assert.assertEquals(key, meta.getKey());
        Assert.assertNull(meta.getData().getClassName());
        Assert.assertNull(meta.getData().getData());

        // and ensure nothing was screwed up in the ciphertext as a result of deserialization
        Assert.assertArrayEquals(data.getEncryptedData().getContents(), meta.getData().getEncryptedData().getContents());
        Assert.assertArrayEquals(data.getEncryptedData().getLength(), meta.getData().getEncryptedData().getLength());
        Assert.assertArrayEquals(data.getEncryptedClassName().getContents(), meta.getData().getEncryptedClassName()
                .getContents());
        Assert.assertArrayEquals(data.getEncryptedClassName().getLength(), meta.getData().getEncryptedClassName()
                .getLength());
    }

    private void initKeylessImplicitEncryption() {
        this.pubKey = null;
        this.privKey = null;

        resetSecurityConfiguration();
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
        initImplicitEncryption();
        BitVector key = BitVectors.randomVector(INDEX_LENGTH);
        Metadata metadatum = new Metadata(new DocumentId("TEST",user), "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> data = new FheEncryptable<Metadata>(metadatum);

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        String actual = serialize(req);

        String expectedSubstring = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key));

        String expectedTypeSubstring = "\"@class\":\"" + data.getClass().getCanonicalName() + "\"";

        // weak substring assertion that does not test ciphertext validity
        // ciphertext validity is covered in serializationDeserializationTest
        Assert.assertThat(actual, CoreMatchers.containsString(expectedSubstring));
        Assert.assertThat(actual, CoreMatchers.containsString(expectedTypeSubstring));
    }

    /**
     * Does serialization of an Encryptable properly throw an exception if you don't properly register public/private
     * keys?
     * 
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Test
    public void testSerializationWithImplicitEncryptionKeyless() throws JsonGenerationException, JsonMappingException,
            IOException {
        initKeylessImplicitEncryption();

        BitVector key = BitVectors.randomVector(INDEX_LENGTH);
        Metadata metadatum = new Metadata(new DocumentId("TEST",user), "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> data = new FheEncryptable<Metadata>(metadatum);

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        boolean caught = false;
        try {
            serialize(req);
        } catch (JsonMappingException e) {
            if (e.getCause() instanceof NullPointerException) {
                caught = true;
            }
        }
        Assert.assertTrue(caught);
    }

    private void resetSecurityConfiguration() {
        this.config = new SecurityConfigurationMapping().add(FheEncryptable.class, this.privKey).add(
                FheEncryptable.class, this.pubKey);
        this.mapper = new KodexObjectMapperFactory().getObjectMapper(config);
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
            IOException, SecurityConfigurationException {
        BitVector key = BitVectors.randomVector(INDEX_LENGTH);
        Metadata metadatum = new Metadata(new DocumentId("TEST",user), "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadata> data = new FheEncryptable<Metadata>(metadatum);

        // explicit encryption
        PrivateKey privKey = new PrivateKey(128, 64);
        PublicKey pubKey = new PublicKey(privKey);

        SecurityConfigurationMapping tmpConfig = new SecurityConfigurationMapping().add(FheEncryptable.class, privKey)
                .add(FheEncryptable.class, pubKey);

        data = (FheEncryptable<Metadata>) data.encrypt(tmpConfig);

        // create the metadataRequest with our (ENCRYPTED) Encryptable
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_CLASS + "\":\"" + data.getClass().getCanonicalName() + "\",\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + serialize(data.getEncryptedData()) + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + serialize(data.getEncryptedClassName()) + "}}]}";

        String actual = serialize(req);

        Assert.assertEquals(expected, actual);
    }

}
