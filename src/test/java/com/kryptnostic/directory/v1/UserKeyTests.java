package com.kryptnostic.directory.v1;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class UserKeyTests {
    private static final String REALM  = "Caemlyn";
    private static final String NAME   = "RandAlthor@kryptnostic.com";
    private static final String L_NAME = StringUtils.lowerCase( NAME );

    @Test
    public void testToFromString() {
        UUID uk = UUID.randomUUID();
        String fqn = uk.toString();
        UUID fromString = UUID.fromString( fqn );
        Assert.assertEquals( uk, fromString );
    }
}
