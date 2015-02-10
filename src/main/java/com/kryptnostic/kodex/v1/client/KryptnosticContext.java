package com.kryptnostic.kodex.v1.client;

import java.util.Map;
import java.util.Set;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.crypto.EncryptedSearchSharingKey;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingEncryptionService;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {

    BitVector generateSearchNonce();

    KryptnosticConnection getConnection();

    /**
     * Get the global hash function required for search. Only make a request if necessary
     * 
     * @return
     * @throws ResourceNotFoundException If hash function does not exist on the server
     */
    SimplePolynomialFunction getGlobalHashFunction() throws ResourceNotFoundException;

    EncryptedSearchSharingKey generateSharingKey();

    BitVector encryptNonce( BitVector nonce );

    byte[] rsaDecrypt( byte[] ciphertext ) throws SecurityConfigurationException;

    byte[] rsaEncrypt( byte[] plaintext ) throws SecurityConfigurationException;

    void submitBridgeKeyWithSearchNonce( String documentId, EncryptedSearchSharingKey sharingKey ) throws IrisException;

    BitVector generateIndexForToken( String token, EncryptedSearchSharingKey sharingKey )
            throws ResourceNotFoundException;

    BitVector prepareSearchToken( String token );

    Map<UserKey, RsaCompressingEncryptionService> getEncryptionServiceForUsers( Set<UserKey> users );

    RsaCompressingCryptoService getRsaCryptoService() throws SecurityConfigurationException;

    EncryptedSearchBridgeKey fromSharingKey( EncryptedSearchSharingKey sharingKey ) throws IrisException;
}
