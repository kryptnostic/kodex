package com.kryptnostic.kodex.v1.client;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cern.colt.bitvector.BitVector;

import com.kryptnostic.crypto.EncryptedSearchBridgeKey;
import com.kryptnostic.crypto.EncryptedSearchSharingKey;
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

    void submitBridgeKeyWithSearchNonce( String objectId, EncryptedSearchSharingKey sharingKey ) throws IrisException;

    byte[] prepareSearchToken( String token );

    Map<UUID, RsaCompressingEncryptionService> getEncryptionServiceForUsers( Set<UUID> users );

    RsaCompressingCryptoService getRsaCryptoService() throws SecurityConfigurationException;

    EncryptedSearchBridgeKey fromSharingKey( EncryptedSearchSharingKey sharingKey ) throws IrisException;

    byte[] generateIndexForToken( String token, byte[] objectSearchKey, byte[] ObjectAddressMatrix );
}
