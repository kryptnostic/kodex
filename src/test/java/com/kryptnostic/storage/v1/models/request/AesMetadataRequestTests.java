package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
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
import com.kryptnostic.crypto.v1.ciphers.CryptoService;
import com.kryptnostic.crypto.v1.ciphers.Cypher;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.linear.BitUtils;

public class AesMetadataRequestTests extends BaseSerializationTest {

    private static final int INDEX_LENGTH = 256;

    private CryptoService crypto;
    private SecurityConfigurationMapping config;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private void initImplicitEncryption() {
        // register key with object mapper
        this.crypto = new CryptoService(Cypher.AES_CTR_PKCS5_128, new BigInteger(130, new SecureRandom()).toString(32)
                .toCharArray());

        resetSecurityConfiguration();
    }

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws IOException, SecurityConfigurationException {
        initImplicitEncryption();

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new AesEncryptable<Metadatum>(metadatum);

        // explicit encryption to generate some json
        data = (AesEncryptable<Metadatum>) data.encrypt(this.config);

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

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new AesEncryptable<Metadatum>(metadatum);

        // explicit encryption to generate some json
        data = data.encrypt(config);

        String encryptedData = serialize(data.getEncryptedData());
        String encryptedClassName = serialize(data.getEncryptedClassName());

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_CLASS + "\":\"" + data.getClass().getCanonicalName() + "\",\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + "}}]}";

        // kill private key!
        this.crypto = null;
        resetSecurityConfiguration();

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
        this.crypto = null;

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
        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new AesEncryptable<Metadatum>(metadatum);

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

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new AesEncryptable<Metadatum>(metadatum);

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
        this.config = new SecurityConfigurationMapping().add(AesEncryptable.class, this.crypto);
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
        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new AesEncryptable<Metadatum>(metadatum);

        // explicit encryption
        CryptoService crypto = new CryptoService(Cypher.AES_CTR_PKCS5_128, "crypto-test".toCharArray());

        SecurityConfigurationMapping tmpConfig = new SecurityConfigurationMapping().add(AesEncryptable.class, crypto);

        data = (AesEncryptable<Metadatum>) data.encrypt(tmpConfig);

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
