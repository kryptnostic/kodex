package com.kryptnostic.kodex.v1.client;

import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

/**
 * KryptnosticContext is responsible for maintaining any shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 *
 */
public interface KryptnosticContext {
    SimplePolynomialFunction getIndexingHashFunction();

}
