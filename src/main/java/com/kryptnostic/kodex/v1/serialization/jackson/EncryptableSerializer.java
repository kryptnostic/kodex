package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kryptnostic.kodex.v1.models.Encryptable;
import com.kryptnostic.kodex.v1.security.SecurityConfiguration;
import com.kryptnostic.kodex.v1.security.SecurityConfigurationMapping;

@SuppressWarnings("rawtypes")
public class EncryptableSerializer extends JsonSerializer<Encryptable> {

    private final SecurityConfigurationMapping securityConfiguration;

    public EncryptableSerializer(SecurityConfigurationMapping securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public void serialize(Encryptable value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonGenerationException {
        Encryptable<?> encryptedValue = value;
        if (!value.isEncrypted()) {
            if (securityConfiguration != null) {
                SecurityConfiguration<?, ?> config = securityConfiguration.get(value.getScheme());
                if (config != null) {
                    Object pubKey = config.getPublicKey();
                    encryptedValue = value.encrypt(pubKey);
                }
            }
        }
        jgen.writeStartObject();
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_DATA, encryptedValue.getEncryptedData());
        jgen.writeObjectField(Encryptable.FIELD_ENCRYPTED_CLASS_NAME, encryptedValue.getEncryptedClassName());
        jgen.writeObjectField(Encryptable.FIELD_SCHEME, encryptedValue.getScheme());
        jgen.writeEndObject();
    }

}
