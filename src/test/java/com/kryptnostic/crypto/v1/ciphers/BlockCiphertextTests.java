package com.kryptnostic.crypto.v1.ciphers;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;

public class BlockCiphertextTests {

    @Test
    public void equalsTest() {
        BlockCiphertext b1 = new BlockCiphertext( null, null, null, null, null );
        BlockCiphertext b2 = new BlockCiphertext( null, null, null, null, null );

        Assert.assertEquals( b1, b2 );

        byte[] iv = { 41, 41 };
        byte[] salt = { 40, 40 };
        byte[] contents = { 10 };
        byte[] length = { 5 };
        byte[] tag = { 27 };

        BlockCiphertext b3 = new BlockCiphertext( iv, salt, contents );
        BlockCiphertext b4 = new BlockCiphertext( iv, salt, contents );

        Assert.assertEquals( b3, b4 );
        Assert.assertNotEquals( b1, b3 );

        BlockCiphertext b5 = new BlockCiphertext(
                new byte[] { 41 },
                salt,
                contents,
                Optional.of( length ),
                Optional.of( tag ) );
        Assert.assertNotEquals( b4, b5 );

        BlockCiphertext b6 = new BlockCiphertext(
                iv,
                new byte[] { 41 },
                contents,
                Optional.of( length ),
                Optional.of( tag ) );
        Assert.assertNotEquals( b4, b6 );

        BlockCiphertext b7 = new BlockCiphertext( iv, salt, new byte[] { 41 }, Optional.of( length ), Optional.of( tag ) );
        Assert.assertNotEquals( b4, b7 );

        BlockCiphertext b8 = new BlockCiphertext(
                iv,
                salt,
                contents,
                Optional.of( new byte[] { 41 } ),
                Optional.of( tag ) );
        Assert.assertNotEquals( b4, b8 );
    }
}
