package com.kryptnostic.kodex.v1.serialization.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.common.base.Optional;
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

    private static final String                 SECURITY_ERROR_MSG = "Security configuration error";
    private final Optional<CryptoServiceLoader> loader;

    /**
     * @param loader
     */
    public EncryptableSerializer( Optional<CryptoServiceLoader> loader ) {
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
        if ( loader.isPresent() || ( !loader.isPresent() && value.isEncrypted() ) ) {
            // Only serialize if we can encrypt or it is already encrypted
            Encryptable<?> encryptedValue = value;
            if ( loader.isPresent() ) {
                // We have a crypto service loader so we can try to encrypt.
                encryptedValue = value.encrypt( loader.get() );
            }
            jgen.writeObjectField( Names.DATA_FIELD, encryptedValue.getEncryptedData() );
            jgen.writeObjectField( Names.USERNAME_FIELD, encryptedValue.getEncryptedClassName() );
            jgen.writeObjectField( Names.KEY_FIELD, encryptedValue.getCryptoServiceId() );
        } else {
            throw new JsonMappingException( "Refusing to serialize unencrypted" );
        }
    }
}
