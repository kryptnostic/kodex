package com.kryptnostic.heracles.v1.keys;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.core.io.ClassPathResource;

public final class Keystores {
    private Keystores() {
    }

    public static KeyStore loadKeystoreFromResource(String resource, char[] password) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = KeyStore.getInstance( "JKS" );
        ks.load( new ClassPathResource( resource ).getInputStream() , password );
        return ks;
    }
}
