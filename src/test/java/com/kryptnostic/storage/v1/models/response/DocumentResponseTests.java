package com.kryptnostic.storage.v1.models.response;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

public class DocumentResponseTests extends AesEncryptableBase {

    // TODO add jackson annotation checker

    @Test
    public void testDeserialize() throws JsonGenerationException, JsonMappingException, IOException,
            SecurityConfigurationException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
            InvalidKeySpecException, InvalidParameterSpecException, SealedKodexException, SignatureException,Exception {
        initImplicitEncryption();
        DocumentResponse resp = new DocumentResponse( AesEncryptableUtils.createEncryptedDocument(
                "document1",
                "test cool thing",
                kodex ), 200, true );

        String serialized = serialize( resp );

        DocumentResponse result = deserialize( serialized, DocumentResponse.class );

        Assert.assertEquals( resp.getData(), result.getData() );
    }
}
