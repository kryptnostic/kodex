package com.kryptnostic.kodex.v1.client;

import java.util.List;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.security.SecurityService;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;
import com.kryptnostic.storage.v1.models.EncryptedSearchDocumentKey;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {
    List<EncryptedSearchDocumentKey> getDocumentKeys();

    void addDocumentKeys( List<EncryptedSearchDocumentKey> keys );

    BitVector generateDocumentNonce();

    SecurityService getSecurityService();

    /**
     * Get the global hash function required for search. Only make a request if necessary
     * 
     * @return
     * @throws ResourceNotFoundException If hash function does not exist on the server
     */
    SimplePolynomialFunction getGlobalHashFunction() throws ResourceNotFoundException;
}
