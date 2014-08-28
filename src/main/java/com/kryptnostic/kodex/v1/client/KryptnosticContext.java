package com.kryptnostic.kodex.v1.client;

import java.util.List;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {
    SimplePolynomialFunction getSearchFunction();

    List<BitVector> getNonces();

    void addNonces(List<BitVector> nonces);

    BitVector generateNonce();
}