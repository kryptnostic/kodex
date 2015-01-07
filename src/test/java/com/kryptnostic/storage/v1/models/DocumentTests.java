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

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.SecurityConfigurationTestUtils;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;

public class DocumentTests extends SecurityConfigurationTestUtils {

    @Test
    public void testEquals() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {

        initializeCryptoService();

        Document d1 = new Document( "cool document" );
        d1 = (Document) d1.encrypt( loader );
        Document d2 = new Document( "cool document" );
        d2 = (Document) d2.encrypt( loader );

        Assert.assertEquals( d1, d1 );
        Assert.assertEquals( d1, d2 );

        Document d3 = new Document( "cool document" );
        d3 = (Document) d3.encrypt( loader );
        Assert.assertNotEquals( d1, d3 );

        Document d4 = new Document( "cool document cool" );
        d4 = (Document) d4.encrypt( loader );
        Assert.assertEquals( d1, d4 );
        Assert.assertNotEquals( d1, d3 );
    }

    @Test
    public void testDocumentSerialization() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        resetSecurity();

        Document doc = AesEncryptableUtils.createEncryptedDocument( "test", "this is a test", kodex );
        String out = serialize( doc );
        Document result = deserialize( out, Document.class );

        Assert.assertEquals( doc, result );
    }

    @Test
    public void testDocumentBlockSerialization() throws SecurityConfigurationException, IOException,
            ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        resetSecurity();

        Document doc = AesEncryptableUtils.createEncryptedDocument( "test", "this is a test", kodex );
        String out = serialize( doc.getBlocks() );
        EncryptableBlock[] result = deserialize( out, EncryptableBlock[].class );

        Assert.assertEquals( doc.getBlocks()[ 0 ].getBlock().decrypt( kodex ).getData(), result[ 0 ].getBlock()
                .decrypt( kodex ).getData() );
    }

    @Test
    public void testDocumentVerification() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        resetSecurity();

        HashFunction hashFunction = Hashing.sha256();

        String body = "this is a test";
        AesEncryptable<String> encryptable = (AesEncryptable<String>) new AesEncryptable<String>( body )
                .encrypt( loader );
        Document doc = AesEncryptableUtils.createEncryptedDocument( "test", body, encryptable );

        EncryptableBlock[] blocks = doc.getBlocks();
        for ( EncryptableBlock block : blocks ) {
            BlockCiphertext encryptableString = block.getBlock();
            Assert.assertEquals( hashFunction.hashBytes( encryptableString.getContents() ).toString(), hashFunction
                    .hashBytes( block.getVerify() ).toString() );
        }

    }
}
