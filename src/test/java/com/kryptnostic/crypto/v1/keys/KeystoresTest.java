package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kryptnostic.crypto.v1.keys.Keystores;

public class KeystoresTest {
    private static final String KEYSTORE = "security/rhizome.jks";
    private static final String CERTIFICATE_ALIAS = "rhizomessl";
    private static KeyStore ks;

    @BeforeClass
    public static void testLoadKeystoreFromResource() throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException {
        ks = Keystores.loadKeystoreFromResource(KEYSTORE, "rhizome".toCharArray());
    }

    @Test
    public void testloadPublicKey() throws KeyStoreException {
        Assert.assertNotNull(ks.getCertificate(CERTIFICATE_ALIAS));
    }
}
