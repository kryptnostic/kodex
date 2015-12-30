package com.kryptnostic.storage.v1.models;

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
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;
import com.kryptnostic.utils.SecurityConfigurationTestUtils;

@SuppressWarnings( "javadoc" )
public class KryptnosticObjectTests extends SecurityConfigurationTestUtils {

    @Test
    public void testEquals() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SignatureException, Exception {
        loader.put( "test", passwordCryptoService );
        loader.put( "test2", passwordCryptoService );

        KryptnosticObject d1 = new KryptnosticObject( new ObjectMetadata( "test", null ), "cool document" )
                .encrypt( loader );
        KryptnosticObject d2 = new KryptnosticObject( new ObjectMetadata( "test", null ), "cool document" )
                .encrypt( loader );

        Assert.assertEquals( d1, d1 );
        Assert.assertEquals( d1, d2 );

        KryptnosticObject d3 = new KryptnosticObject( new ObjectMetadata( "test2", null ), "cool document" )
                .encrypt( loader );
        Assert.assertNotEquals( d1, d3 );

        KryptnosticObject d4 = new KryptnosticObject( new ObjectMetadata( "test", null ), "cool document cool" )
                .encrypt( loader );
        Assert.assertEquals( d1, d4 );
        Assert.assertNotEquals( d1, d3 );
    }

    @Test
    @Ignore
    public void testDocumentSerialization() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SignatureException, Exception {
        loader.put( "test", passwordCryptoService );

        KryptnosticObject doc = new KryptnosticObject( new ObjectMetadata( "test", null ), "this is a test" );
        String out = serialize( doc );
        KryptnosticObject result = deserialize( out, KryptnosticObject.class );

        Assert.assertEquals( doc, result );
    }

    @Test
    public void testDocumentBlockSerialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SignatureException, Exception {
        loader.put( "test", passwordCryptoService );

        KryptnosticObject doc = new KryptnosticObject( new ObjectMetadata( "test", null ), "this is a test" )
                .encrypt( loader );
        String out = serialize( doc.getBody().getEncryptedData() );
        EncryptableBlock[] result = deserialize( out, EncryptableBlock[].class );

        Assert.assertEquals( doc.getBody().decrypt( loader ).getData(), passwordCryptoService.decrypt( result[ 0 ].getBlock() ) );
    }

    @Test
    public void testDocumentVerification() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SignatureException, Exception {
        HashFunction hashFunction = Encryptable.hashFunction;

        loader.put( "test", passwordCryptoService );

        KryptnosticObject doc = new KryptnosticObject( new ObjectMetadata( "test", null ), "this is a test" )
                .encrypt( loader );

        EncryptableBlock[] blocks = doc.getBody().getEncryptedData();
        for ( EncryptableBlock block : blocks ) {
            BlockCiphertext encryptableString = block.getBlock();
            Assert.assertEquals(
                    HashCode.fromBytes( block.getVerify() ).toString(),
                    hashFunction.hashBytes( encryptableString.getContents() ).toString() );
        }

    }
}
