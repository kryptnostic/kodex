package com.kryptnostic.kodex.v1.constants;

import org.junit.Assert;
import org.junit.Test;

public class NamesTest {

    /**
     *  Changing this suffix could break existing users 
     *  so this is here to make sure that something breaks when it changes
     */
    @Test
    public void testRegistrarSuffixHasntChanged() {
        Assert.assertTrue( Names.REGISTRAR_USER_SUFFIX == "_registrar" );
    }
}
