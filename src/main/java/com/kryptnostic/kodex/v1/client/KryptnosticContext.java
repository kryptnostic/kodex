package com.kryptnostic.kodex.v1.client;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.crypto.EncryptedSearchSharingKey;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.security.CredentialService;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.sharing.v1.DocumentId;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {

    BitVector generateSearchNonce();

    CredentialService getSecurityService();

    /**
     * Get the global hash function required for search. Only make a request if necessary
     * 
     * @return
     * @throws ResourceNotFoundException If hash function does not exist on the server
     */
    SimplePolynomialFunction getGlobalHashFunction() throws ResourceNotFoundException;

    EncryptedSearchSharingKey generateSharingKey();

    BitVector encryptNonce( BitVector nonce );

    void submitBridgeKeyWithSearchNonce(
            DocumentId documentId,
            EncryptedSearchSharingKey sharingKey,
            BitVector searchNonce ) throws IrisException;

    BitVector generateIndexForToken( String token, BitVector searchNonce, EncryptedSearchSharingKey sharingKey )
            throws ResourceNotFoundException;
}
