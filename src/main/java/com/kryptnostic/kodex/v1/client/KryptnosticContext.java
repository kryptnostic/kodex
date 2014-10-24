package com.kryptnostic.kodex.v1.client;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.kodex.v1.exceptions.types.ResourceNotFoundException;
import com.kryptnostic.kodex.v1.security.SecurityService;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {

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
