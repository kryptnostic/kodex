package com.kryptnostic.kodex.v1.crypto.ciphers;

import java.io.IOException;
import java.security.PublicKey;

import com.kryptnostic.kodex.v1.exceptions.types.SecurityConfigurationException;
import com.kryptnostic.kodex.v1.marshalling.DeflatingJacksonMarshaller;

/**
 * A public key based encryptiong service suitable for encrypting other keys.
 * 
 * Due to the limited number of bytes supported by OAEP it compresses data prior
 * to encrypting it to maximize the amount of data is supports.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt; 
 *
 */
public class RsaCompressingEncryptionService {
    protected static final DeflatingJacksonMarshaller marshaller = new DeflatingJacksonMarshaller();

    private final PublicKey                           publicKey;

    protected final Cypher                            cypher;

    public RsaCompressingEncryptionService( Cypher cypher, PublicKey publicKey ) throws SecurityConfigurationException {
        if ( !cypher.getAlgorithm().equals( CryptoAlgorithm.RSA ) ) {
            throw new SecurityConfigurationException( "Only RSA is supported for ths Rsa Compressing Cryptoservice." );
        }
        this.cypher = cypher;
        this.publicKey = publicKey;
    }

    public byte[] encrypt( Object object ) throws SecurityConfigurationException, IOException {
        return Cyphers.encrypt( cypher, publicKey, marshaller.toBytes( object ) );
    }
}
