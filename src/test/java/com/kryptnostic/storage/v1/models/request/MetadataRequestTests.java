package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.BaseSerializationTest;
import com.kryptnostic.bitwise.BitVectors;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadatum;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.models.Encryptable.EncryptionScheme;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;
import com.kryptnostic.kodex.v1.serialization.jackson.KodexObjectMapperFactory;
import com.kryptnostic.linear.BitUtils;

public class MetadataRequestTests extends BaseSerializationTest {

    private static final int INDEX_LENGTH = 256;

    private PrivateKey privKey;
    private PublicKey pubKey;

    private void initImplicitEncryption() {
        // register key with object mapper
        this.privKey = new PrivateKey(128, 64);
        this.pubKey = new PublicKey(privKey);

        SecurityConfigurationMapping config = new SecurityConfigurationMapping().add(EncryptionScheme.FHE, pubKey,
                privKey);

        this.mapper = new KodexObjectMapperFactory().getObjectMapper(config);
    }

    @Test
    /**
     * Does implicit deserialization produce an Encryptable in a PLAIN state in the PRESENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserialization() throws IOException {
        initImplicitEncryption();

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new Encryptable<Metadatum>(metadatum, EncryptionScheme.FHE);

        // explicit encryption to generate some json
        data = data.encrypt(pubKey);

        String encryptedData = serialize(data.getEncryptedData());
        String encryptedClassName = serialize(data.getEncryptedClassName());

        System.out.println(encryptedData);
        System.out.println(encryptedClassName);

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + ",\""
                + Encryptable.FIELD_SCHEME + "\":\"" + EncryptionScheme.FHE + "\"}}]}";

        MetadataRequest deserialized = deserialize(expected, MetadataRequest.class);

        IndexedMetadata meta = deserialized.getMetadata().iterator().next();

        Assert.assertEquals(key, meta.getKey());
        Assert.assertEquals(metadatum.getClass().getName(), meta.getData().getClassName());
        Assert.assertEquals(metadatum, meta.getData().getData());
    }

    @Test
    /**
     * Does implicit deserialization produces an Encryptable in an ENCRYPTED state in the ABSENCE of a private key?
     * @throws IOException
     */
    public void testImplicitDeserializationKeyless() throws IOException {
        initKeylessImplicitEncryption();

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new Encryptable<Metadatum>(metadatum, EncryptionScheme.FHE);

        // explicit encryption to generate some json
        data = data.encrypt(pubKey);

        String encryptedData = serialize(data.getEncryptedData());
        String encryptedClassName = serialize(data.getEncryptedClassName());

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + encryptedData + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + encryptedClassName + ",\""
                + Encryptable.FIELD_SCHEME + "\":\"" + EncryptionScheme.FHE + "\"}}]}";

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
        initImplicitEncryption();

        // remove private key and reset objectMapper
        this.privKey = null;
        this.mapper = new KodexObjectMapperFactory().getObjectMapper(null);
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
        Encryptable<Metadatum> data = new Encryptable<Metadatum>(metadatum, EncryptionScheme.FHE);

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        String actual = serialize(req);

        String expectedSubstring = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key))
                + ",\"data\":{\"" + Encryptable.FIELD_ENCRYPTED_DATA + "\":";

        // weak substring assertion that does not test ciphertext validity
        // ciphertext validity is covered in serializationDeserializationTest
        Assert.assertThat(actual, CoreMatchers.containsString(expectedSubstring));
    }

    @Test
    /**
     * Does serialization of an Encryptable properly produce null fields if you don't properly register public/private keys?
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void testSerializationWithImplicitEncryptionKeyless() throws JsonGenerationException, JsonMappingException,
            IOException {
        initKeylessImplicitEncryption();
        // kill the pubkey
        this.pubKey = null;

        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new Encryptable<Metadatum>(metadatum, EncryptionScheme.FHE);

        // implicit encryption via objectmapper

        // Create our request with our (PLAIN) Encryptable. It will get encrypted upon serialization
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        String actual = serialize(req);

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":null,\"" + Encryptable.FIELD_ENCRYPTED_CLASS_NAME
                + "\":null,\"" + Encryptable.FIELD_SCHEME + "\":" + wrapQuotes(EncryptionScheme.FHE.toString())
                + "}}]}";

        // ensure our serialization leaks no information and is completely null
        Assert.assertEquals(actual, expected);
    }

    @Test
    /**
     * Does serialization of an Encryptable work if you opt to encrypt it explicitly, rather than registering an FHE SecurityConfiguration?
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public void testSerializationWithExplicitEncryption() throws JsonGenerationException, JsonMappingException,
            IOException {
        BitVector key = BitUtils.randomVector(INDEX_LENGTH);
        Metadatum metadatum = new Metadatum("TEST", "test", Arrays.asList(1, 2, 3));
        Encryptable<Metadatum> data = new Encryptable<Metadatum>(metadatum, EncryptionScheme.FHE);

        // explicit encryption
        PrivateKey privKey = new PrivateKey(128, 64);
        PublicKey pubKey = new PublicKey(privKey);
        data = data.encrypt(pubKey);

        // create the metadataRequest with our (ENCRYPTED) Encryptable
        MetadataRequest req = new MetadataRequest(Arrays.asList(new IndexedMetadata(key, data)));

        String expected = "{\"metadata\":[{\"key\":" + wrapQuotes(BitVectors.marshalBitvector(key)) + ",\"data\":{\""
                + Encryptable.FIELD_ENCRYPTED_DATA + "\":" + serialize(data.getEncryptedData()) + ",\""
                + Encryptable.FIELD_ENCRYPTED_CLASS_NAME + "\":" + serialize(data.getEncryptedClassName()) + ",\""
                + Encryptable.FIELD_SCHEME + "\":\"" + EncryptionScheme.FHE + "\"}}]}";

        String actual = serialize(req);

        Assert.assertEquals(expected, actual);
    }
}
