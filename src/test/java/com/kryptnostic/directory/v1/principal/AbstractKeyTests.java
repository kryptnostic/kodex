package com.kryptnostic.directory.v1.principal;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.directory.v1.model.AbstractKey;

public class AbstractKeyTests {

    @Test
    public void testValidUsernames() {
        Assert.assertTrue( AbstractKey.isValidUsername( "Marshall" ) );
        Assert.assertTrue( AbstractKey.isValidUsername( "Emin3m" ) );
        Assert.assertTrue( AbstractKey.isValidUsername( "marsh@mathers.edu" ) );
    }

    @Test
    public void testValidRealms() {
        Assert.assertTrue( AbstractKey.isValidRealm( "Marshall" ) );
        Assert.assertTrue( AbstractKey.isValidRealm( "Emin3m" ) );
        Assert.assertTrue( AbstractKey.isValidRealm( "slimshady.com" ) );
        Assert.assertTrue( AbstractKey.isValidRealm( "Emin3m-shady_marsh.d12.com" ) );
        Assert.assertFalse( AbstractKey.isValidRealm( "lala|las" ) );
        Assert.assertFalse( AbstractKey.isValidRealm( "mustbeansos?" ) );
        Assert.assertFalse( AbstractKey.isValidRealm( "holywhackunlyricallyricsandre!" ) );
        Assert.assertFalse( AbstractKey.isValidRealm( "i forgot my name" ) );
    }

}
