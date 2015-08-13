package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

/**
 * @author sinaiman
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt; 
 *
 */
@SuppressWarnings( "rawtypes" )
public class EncryptableSerializer extends JsonSerializer<Encryptable> {

    private static final String       SECURITY_ERROR_MSG = "Security configuration error";
    private final CryptoServiceLoader loader;

    /**
     * @param loader
     */
    public EncryptableSerializer( CryptoServiceLoader loader ) {
        this.loader = loader;
    }

    @Override
    public void serializeWithType(
            Encryptable value,
            JsonGenerator jgen,
            SerializerProvider provider,
            TypeSerializer typeSer ) throws IOException, JsonProcessingException {
        typeSer.writeTypePrefixForObject( value, jgen );
        try {
            writeFields( value, jgen, provider );
        } catch ( SecurityConfigurationException | ClassNotFoundException e ) {
            throw new IOException( SECURITY_ERROR_MSG, e );
        }
        typeSer.writeTypeSuffixForObject( value, jgen );
    };

    @Override
    public void serialize( Encryptable value, JsonGenerator jgen, SerializerProvider provider ) throws IOException,
            JsonGenerationException {
        jgen.writeStartObject();
        try {
            writeFields( value, jgen, provider );
        } catch ( SecurityConfigurationException | ClassNotFoundException e ) {
            throw new IOException( SECURITY_ERROR_MSG, e );
        }
        jgen.writeEndObject();
    }

    private void writeFields( Encryptable value, JsonGenerator jgen, SerializerProvider provider ) throws IOException,
            SecurityConfigurationException, ClassNotFoundException {
        Encryptable<?> encryptedValue = value.encrypt( loader );
        jgen.writeObjectField( Names.DATA_FIELD, encryptedValue.getEncryptedData() );
        jgen.writeObjectField( Names.USERNAME_FIELD, encryptedValue.getEncryptedClassName() );
        jgen.writeObjectField( Names.KEY_FIELD, encryptedValue.getCryptoServiceId() );
    }
}
