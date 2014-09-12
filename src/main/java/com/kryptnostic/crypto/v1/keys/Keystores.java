package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import com.google.common.io.Resources;

public final class Keystores {
    private Keystores() {
    }

    public static KeyStore loadKeystoreFromResource(String resource, char[] password) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(Resources.getResource(resource).openStream(), password);
        return ks;
    }
}
