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
import org.junit.Test;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.kryptnostic.SecurityConfigurationTestUtils;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.serialization.crypto.Encryptable;

@SuppressWarnings( "javadoc" )
public class DocumentTests extends SecurityConfigurationTestUtils {

    @Test
    public void testEquals() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        initializeCryptoService();

        loader.put( "test", crypto );
        loader.put( "test2", crypto );

        Document d1 = new Document( new DocumentMetadata( "test" ), "cool document" ).encrypt( loader );
        Document d2 = new Document( new DocumentMetadata( "test" ), "cool document" ).encrypt( loader );

        Assert.assertEquals( d1, d1 );
        Assert.assertEquals( d1, d2 );

        Document d3 = new Document( new DocumentMetadata( "test2" ), "cool document" ).encrypt( loader );
        Assert.assertNotEquals( d1, d3 );

        Document d4 = new Document( new DocumentMetadata( "test" ), "cool document cool" ).encrypt( loader );
        Assert.assertEquals( d1, d4 );
        Assert.assertNotEquals( d1, d3 );
    }

    @Test
    public void testDocumentSerialization() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        initializeCryptoService();

        loader.put( "test", crypto );

        Document doc = new Document( new DocumentMetadata( "test" ), "this is a test" );
        String out = serialize( doc );
        Document result = deserialize( out, Document.class );

        Assert.assertEquals( doc, result );
    }

    @Test
    public void testDocumentBlockSerialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        initializeCryptoService();

        loader.put( "test", crypto );

        Document doc = new Document( new DocumentMetadata( "test" ), "this is a test" ).encrypt( loader );
        String out = serialize( doc.getBody().getEncryptedData() );
        EncryptableBlock[] result = deserialize( out, EncryptableBlock[].class );

        Assert.assertEquals( doc.getBody().decrypt( loader ).getData(), crypto.decrypt( result[ 0 ].getBlock() ) );
    }

    @Test
    public void testDocumentVerification() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        initializeCryptoService();

        HashFunction hashFunction = Encryptable.hashFunction;

        loader.put( "test", crypto );

        Document doc = new Document( new DocumentMetadata( "test" ), "this is a test" ).encrypt( loader );

        EncryptableBlock[] blocks = doc.getBody().getEncryptedData();
        for ( EncryptableBlock block : blocks ) {
            BlockCiphertext encryptableString = block.getBlock();
            Assert.assertEquals(
                    HashCode.fromBytes( block.getVerify() ).toString(),
                    hashFunction.hashBytes( encryptableString.getContents() ).toString() );
        }

    }
}
