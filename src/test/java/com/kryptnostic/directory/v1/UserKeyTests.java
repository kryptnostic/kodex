package com.kryptnostic.directory.v1;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.directory.v1.model.AbstractKey;
import com.kryptnostic.directory.v1.principal.UserKey;

public class UserKeyTests {
    private static final String REALM  = "Caemlyn";
    private static final String NAME   = "RandAlthor@kryptnostic.com";
    private static final String L_NAME = StringUtils.lowerCase( NAME );

    @Test
    public void testToFromString() {
        UserKey uk = new UserKey( REALM, NAME );
        String fqn = uk.toString();
        UserKey fromString = UserKey.fromString( fqn );
        Assert.assertEquals( uk, fromString );
    }

    @Test
    public void testCaseSensitivity() {
        Assert.assertEquals( new UserKey( REALM, L_NAME ), new UserKey( REALM, NAME ) );
        Assert.assertEquals( new UserKey( REALM, L_NAME ), UserKey.fromString( REALM + AbstractKey.DELIMITER + L_NAME ) );
    }

}
