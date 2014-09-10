package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@SuppressWarnings("rawtypes")
public class EncryptableSerializer extends JsonSerializer<Encryptable> {

    private final SecurityConfigurationMapping securityConfiguration;

    public EncryptableSerializer(SecurityConfigurationMapping securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void serializeWithType(Encryptable value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException, JsonProcessingException {
        typeSer.writeTypePrefixForObject(value, jgen);
        writeFields(value, jgen, provider);
        typeSer.writeTypeSuffixForObject(value, jgen);
    };

    @Override
    public void serialize(Encryptable value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonGenerationException {
        jgen.writeStartObject();
        writeFields(value, jgen, provider);
        jgen.writeEndObject();
    }

    private void writeFields(Encryptable value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        Encryptable<?> encryptedValue = value.encrypt(securityConfiguration);
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_DATA, encryptedValue.getEncryptedData());
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_CLASS_NAME, encryptedValue.getEncryptedClassName());
    }
}
