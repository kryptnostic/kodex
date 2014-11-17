package com.kryptnostic.kodex.v1.security;

import com.kryptnostic.crypto.EncryptedSearchPrivateKey;
import com.kryptnostic.crypto.PrivateKey;
import com.kryptnostic.crypto.PublicKey;
import com.kryptnostic.crypto.v1.ciphers.ICryptoService;
import com.kryptnostic.crypto.v1.keys.Kodex;
import com.kryptnostic.kodex.v1.storage.DataStore;
import com.kryptnostic.users.v1.UserKey;

/**
 * The KryptnosticConnection abstracts out persistence, key generation, and server connection information for down stream consumers.
 * 
 * @author Sina Iman &lt;sina@kryptnostic.com&gt;
 * @author   Nick Hewitt &lt;nick@kryptnostic.com&gt;
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface KryptnosticConnection {
    /**
     * @return The search kodex containing {@link ICryptoService}s for decrypting shared objects.
     */
    Kodex<String> getKodex();

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
