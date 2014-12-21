package com.kryptnostic.heracles.signatures.v1;

/**
 * Represent
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface Signable<T> {
    T sign( byte[] signature );
    byte[] getBytesForSignature();
    byte[] getSignature();
    boolean isSigned(); 
}
