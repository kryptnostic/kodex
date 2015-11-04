package com.kryptnostic.kodex.v1.client;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingCryptoService;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.storage.DataStore;
import com.kryptnostic.krypto.engine.KryptnosticEngine;
import com.kryptnostic.storage.v1.http.KeyStorageApi;
import com.kryptnostic.v2.crypto.CryptoServiceLoader;

/**
 * The KryptnosticConnection abstracts out persistence, key generation, and server connection information for down
 * stream consumers.
 * 
 * @author Sina Iman &lt;sina@kryptnostic.com&gt;
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface KryptnosticConnection {
    CryptoServiceLoader getCryptoServiceLoader();

    RsaCompressingCryptoService getRsaCryptoService() throws SecurityConfigurationException;

    String getUserCredential();

    PrivateKey getRsaPrivateKey();

    PublicKey getRsaPublicKey();

    UUID getUserId();

    String getUrl();

    DataStore getDataStore();

    KryptnosticEngine getKryptnosticEngine();

    KeyStorageApi getCryptoKeyStorageApi();

    byte[] getClientHashFunction();
}
