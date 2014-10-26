package com.kryptnostic.storage.v1.models.utils;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.primitives.Chars;
import com.kryptnostic.crypto.v1.keys.Kodex.SealedKodexException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.models.AesEncryptable;
import com.kryptnostic.kodex.v1.models.utils.AesEncryptableUtils;
import com.kryptnostic.storage.v1.models.request.AesEncryptableBase;

@Ignore
public class AesEncryptableUtilsTests extends AesEncryptableBase {

    @Test
    public void platformAssumptionsTest() throws SecurityConfigurationException, InvalidKeyException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException,
            SealedKodexException, IOException, SignatureException, Exception {
        initImplicitEncryption();

        final int PADDING = 2;

        AesEncryptable<String> enc = new AesEncryptable<String>( StringUtils.newStringUtf8( ""
                .getBytes( Charsets.UTF_8 ) ) );
        Assert.assertEquals( 0 + PADDING, enc.encrypt( kodex ).getEncryptedData().getContents().length );

        enc = new AesEncryptable<String>( StringUtils.newStringUtf8( "a".getBytes( Charsets.UTF_8 ) ) );
        Assert.assertEquals( 2 + PADDING, enc.encrypt( kodex ).getEncryptedData().getContents().length );

        enc = new AesEncryptable<String>( StringUtils.newStringUtf8( "ab".getBytes( Charsets.UTF_8 ) ) );
        Assert.assertEquals( 4 + PADDING, enc.encrypt( kodex ).getEncryptedData().getContents().length );
    }

    @Test
    public void shortStringChunkTest() throws SecurityConfigurationException, IOException, ClassNotFoundException,
            InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidParameterSpecException, SealedKodexException, SignatureException, Exception {
        initImplicitEncryption();
        List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString( "cool", kodex );

        Assert.assertEquals( 1, results.size() );
        Assert.assertTrue( results.get( 0 ).isEncrypted() );
        Assert.assertEquals( 4 * Chars.BYTES, results.get( 0 ).getEncryptedData().getContents().length );
        Assert.assertEquals( 4 * Chars.BYTES, results.get( 0 ).decrypt( kodex ).getData().getBytes().length );
    }

    @Test
    public void longStringChunkTest() throws SecurityConfigurationException, InvalidKeyException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, InvalidParameterSpecException,
            SealedKodexException, IOException, SignatureException, Exception {
        initImplicitEncryption();
        String str = StringUtils.newStringUtf8( fillByteArray( 4096 ) );
        List<AesEncryptable<String>> results = AesEncryptableUtils.chunkString( str, kodex );

        Assert.assertEquals( 1, results.size() );

        str = StringUtils.newStringUtf8( fillByteArray( 4096 + 1 ) );

        results = AesEncryptableUtils.chunkString( str, kodex );

        Assert.assertEquals( 2, results.size() );
    }

    private byte[] fillByteArray( int sz ) {
        byte[] str = new byte[ sz ];

        for ( int i = 0; i < sz; i++ ) {
            str[ i ] = 42;
        }

        return str;
    }

}
