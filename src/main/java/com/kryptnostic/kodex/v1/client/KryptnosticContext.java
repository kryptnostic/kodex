package com.kryptnostic.kodex.v1.client;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.kryptnostic.indexing.v1.ServerIndexPair;
import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.RsaCompressingEncryptionService;
import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;

/**
 * KryptnosticContext is responsible for maintaining shared state between the KryptnosticClient and Kryptnostic
 * services.
 * 
 * @author Nick Hewitt &lt;nick@kryptnostic.com&gt;
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public interface KryptnosticContext {

    KryptnosticConnection getConnection();

    byte[] rsaDecrypt( byte[] ciphertext ) throws SecurityConfigurationException;

    byte[] rsaEncrypt( byte[] plaintext ) throws SecurityConfigurationException;

    void addIndexPair( String objectId, ServerIndexPair indexPair );

    void addIndexPairs( Map<String, ServerIndexPair> indexPairs );

    byte[] prepareSearchToken( String token );

    Map<UUID, RsaCompressingEncryptionService> getEncryptionServiceForUsers( Set<UUID> users );

    RsaCompressingCryptoService getRsaCryptoService() throws SecurityConfigurationException;

    byte[] generateIndexForToken( String token, byte[] objectSearchKey, byte[] objectAddressMatrix );

}
