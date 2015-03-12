package com.kryptnostic.directory.v1.principal;

import org.junit.Assert;
import org.junit.Test;

import com.kryptnostic.directory.v1.model.AbstractKey;

public class UserKeyTests {

    public static final String REALM          = "coNan.org";
    public static final String USERNAME       = "conan@conan.org";
    public static final String USERNAME_CAP   = "Conan@conan.org";
    public static final String USERNAME_DELIM = "con|an@conan.org";

    @Test
    public void testFromString() {
        String validKeyString = REALM + AbstractKey.DELIMITER + USERNAME;
        Assert.assertEquals( new UserKey( REALM, USERNAME ), UserKey.fromString( validKeyString ) );

    }

    @Test
    public void testDelimeterInUsername() {
        String fqn = REALM + AbstractKey.DELIMITER + USERNAME_DELIM;
        UserKey found = UserKey.fromString( fqn );
        Assert.assertEquals( REALM, found.getRealm() );
        Assert.assertEquals( USERNAME_DELIM, found.getName() );
    }

    @Test
    public void testToFromString() {
        UserKey expected = new UserKey( REALM, USERNAME );
        Assert.assertEquals( expected, UserKey.fromString( expected.toString() ) );
    }

    @Test
    public void testUsernameCaseInsensitive() {
        Assert.assertEquals( new UserKey( REALM, USERNAME ), new UserKey( REALM, USERNAME_CAP ) );
        Assert.assertEquals(
                UserKey.fromString( REALM + AbstractKey.DELIMITER + USERNAME ),
                UserKey.fromString( REALM + AbstractKey.DELIMITER + USERNAME_CAP ) );

    }

}
