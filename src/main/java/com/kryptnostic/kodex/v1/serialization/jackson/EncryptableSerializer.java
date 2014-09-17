package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@SuppressWarnings("rawtypes")
public class EncryptableSerializer extends JsonSerializer<Encryptable> {

    private static final String SECURITY_ERROR_MSG = "Security configuration error";
    private final SecurityConfigurationMapping securityConfiguration;

    public EncryptableSerializer(SecurityConfigurationMapping securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void serializeWithType(Encryptable value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException, JsonProcessingException {
        typeSer.writeTypePrefixForObject(value, jgen);
        try {
            writeFields(value, jgen, provider);
        } catch (SecurityConfigurationException e) {
            throw new IOException(SECURITY_ERROR_MSG, e);
        }
        typeSer.writeTypeSuffixForObject(value, jgen);
    };

    @Override
    public void serialize(Encryptable value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonGenerationException {
        jgen.writeStartObject();
        try {
            writeFields(value, jgen, provider);
        } catch (SecurityConfigurationException e) {
            throw new IOException(SECURITY_ERROR_MSG, e);
        }
        jgen.writeEndObject();
    }

    private void writeFields(Encryptable value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            SecurityConfigurationException {
        Encryptable<?> encryptedValue = value.encrypt(securityConfiguration);
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_DATA, encryptedValue.getEncryptedData());
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_CLASS_NAME, encryptedValue.getEncryptedClassName());
    }
}
