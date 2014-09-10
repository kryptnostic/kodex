package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.google.common.collect.Lists;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.models.FheEncryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfiguration;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@SuppressWarnings("rawtypes")
public class EncryptableDeserializer extends JsonDeserializer<Encryptable> {
    private final SecurityConfigurationMapping securityConfiguration;

    public EncryptableDeserializer(SecurityConfigurationMapping securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public Encryptable deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer)
            throws IOException, JsonProcessingException {
        
        
        return null;
    }

    @Override
    public Encryptable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        JsonNode encryptedDataNode = node.get(Encryptable.FIELD_ENCRYPTED_DATA);
        JsonNode encryptedClassNameNode = node.get(Encryptable.FIELD_ENCRYPTED_CLASS_NAME);

        Ciphertext encryptedData = deserializeCiphertext(encryptedDataNode);
        Ciphertext encryptedClassName = deserializeCiphertext(encryptedClassNameNode);

        FheEncryptable<?> encryptedObject = new FheEncryptable(encryptedData, encryptedClassName);

        // decrypt if private key is available
        return decryptIfPossible(encryptedObject);
    }

    private Ciphertext deserializeCiphertext(JsonNode encryptedNode) throws IOException {
        if (encryptedNode.isNull()) {
            return null;
        }
        return new Ciphertext(encryptedNode.get("contents").binaryValue(), getLengthsArray(encryptedNode));
    }

    /**
     * If a private key is available, decrypt the encryptedObject and return plain, otherwise return the still-encrypted
     * object
     * 
     * This locates a private key based on a securityConfiguration
     * 
     * @param encryptedObject
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private Encryptable decryptIfPossible(Encryptable<?> encryptedObject) throws JsonParseException,
            JsonMappingException, IOException {
        Encryptable decryptedObject = null;
        if (securityConfiguration != null) {
            SecurityConfiguration<?, ?> config = securityConfiguration.get(encryptedObject.getClass());
            if (config != null) {
                Object privateKey = config.getPrivateKey();
                if (privateKey != null) {
                    try {
                        decryptedObject = encryptedObject.decrypt(securityConfiguration);
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return decryptedObject;
                }
            }
        }
        return encryptedObject;
    }

    /**
     * In deserializing a Ciphertext, this method reads the long array of lengths
     * 
     * @param encryptedNode
     * @return
     */
    private long[] getLengthsArray(JsonNode encryptedNode) {
        Collection<Long> lengths = Lists.newArrayList();
        Iterator<JsonNode> iter = encryptedNode.get("length").iterator();
        while (iter.hasNext()) {
            JsonNode lenNode = iter.next();
            lengths.add(lenNode.asLong());
        }
        return ArrayUtils.toPrimitive(lengths.toArray(new Long[0]));
    }
}
