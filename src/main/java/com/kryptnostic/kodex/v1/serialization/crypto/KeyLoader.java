package com.kryptnostic.kodex.v1.serialization.crypto;

import java.security.PrivateKey;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheLoader;
import com.kryptnostic.directory.v1.http.DirectoryApi;
import com.kryptnostic.kodex.v1.crypto.ciphers.AesCryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cyphers;
import com.kryptnostic.kodex.v1.marshalling.DeflatingJacksonMarshaller;
import com.kryptnostic.sharing.v1.models.DocumentId;

public class KeyLoader extends CacheLoader<DocumentId, AesCryptoService> {
    private static final DeflatingJacksonMarshaller marshaller = new DeflatingJacksonMarshaller();
    private final Cypher                            cypher;
    private final DirectoryApi                            keys;
    private final PrivateKey                        privateKey;

    public KeyLoader( Cypher cypher, DirectoryApi keys , PrivateKey privateKey ) {
        this.cypher = cypher;
        this.keys = keys;
        this.privateKey = privateKey;
    }
    
    @Override
    public AesCryptoService load( DocumentId key ) throws Exception {
        return marshaller.fromBytes(
                Cyphers.decrypt( cypher, privateKey, Preconditions.checkNotNull(
                        keys.getDocumentId( key.getDocumentId() ).getData(),
                        "Key cannot be null." ) ),
                AesCryptoService.class );
    }

}
