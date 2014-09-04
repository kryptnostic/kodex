package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.hazelcast.util.Base64;
import com.kryptnostic.crypto.Ciphertext;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfiguration;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@SuppressWarnings("rawtypes")
public class EncryptableDeserializer extends JsonDeserializer<Encryptable> {
    private final SecurityConfigurationMapping securityConfiguration;

    public EncryptableDeserializer(SecurityConfigurationMapping securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public Encryptable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Encryptable<?> encryptedObject = null;

        // data
        JsonNode encryptedDataNode = node.get(Encryptable.FIELD_ENCRYPTED_DATA);
        Collection<Long> dataLengths = Lists.newArrayList();
        Iterator<JsonNode> iter = encryptedDataNode.get("length").iterator();
        while (iter.hasNext()) {
            JsonNode lenNode = iter.next();
            dataLengths.add(lenNode.asLong());
        }

        // cls
        JsonNode encryptedClassNameNode = node.get(Encryptable.FIELD_ENCRYPTED_CLASS_NAME);
        Collection<Long> classNameLengths = Lists.newArrayList();
        Iterator<JsonNode> iterc = encryptedClassNameNode.get("length").iterator();
        while (iterc.hasNext()) {
            JsonNode lenNode = iterc.next();
            classNameLengths.add(lenNode.asLong());
        }

        Ciphertext encryptedData = new Ciphertext(encryptedDataNode.get("contents").binaryValue(),
                ArrayUtils.toPrimitive(dataLengths.toArray(new Long[0])));
        Ciphertext encryptedClassName = new Ciphertext(encryptedClassNameNode.get("contents").binaryValue(),
                ArrayUtils.toPrimitive(classNameLengths.toArray(new Long[0])));
        Encryptable.EncryptionScheme scheme = Encryptable.EncryptionScheme.valueOf(node.get(Encryptable.FIELD_SCHEME)
                .asText());
        encryptedObject = new Encryptable(encryptedData, encryptedClassName, scheme);

        Encryptable decryptedObject = null;

        // decrypt if private key is available
        if (securityConfiguration != null) {
            SecurityConfiguration<?, ?> config = securityConfiguration.get(scheme);
            if (config != null) {
                Object privateKey = config.getPrivateKey();
                if (privateKey != null) {
                    try {
                        decryptedObject = encryptedObject.decrypt(privateKey);
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
}
