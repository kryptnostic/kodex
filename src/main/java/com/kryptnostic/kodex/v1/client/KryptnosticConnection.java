package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.crypto.ciphers.ICryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingCryptoService;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;
import com.kryptnostic.kodex.v1.crypto.keys.Kodex;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.storage.DataStore;

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
    /**
     * @return The search kodex containing {@link ICryptoService}s for decrypting shared objects.
     */
    Kodex<String> getKodex();

    CryptoServiceLoader getCryptoServiceLoader();

    RsaCompressingCryptoService getRsaCryptoService() throws SecurityConfigurationException;

    PrivateKey getFhePrivateKey();

    PublicKey getFhePublicKey();

    String getUserCredential();

    java.security.PrivateKey getRsaPrivateKey();

    java.security.PublicKey getRsaPublicKey();

    UserKey getUserKey();

    String getUrl();

    DataStore getDataStore();

    EncryptedSearchPrivateKey getEncryptedSearchPrivateKey();
}