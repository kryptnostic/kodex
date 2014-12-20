package com.kryptnostic.kodex.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.CipherDescription;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.kodex.v1.crypto.keys.PublicKeyAlgorithm;

public class KodexConfiguration {
    public static final String       KEY_CIPHER_FIELD           = "keyCipher";
    public static final String       DATA_CIPHER_FIELD          = "dataCipher";
    public static final String       PUBLIC_KEY_ALGORITHM_FIELD = "publicKeyAlgorithm";
    public static final String       PUBLIC_KEY_SIZE            = "publicKeySize";

    private final int                publicKeySize;
    private final Cypher             keyCipher;
    private final Cypher             dataCipher;
    private final PublicKeyAlgorithm publicKeyAlgorithm;

    public KodexConfiguration(
            @JsonProperty( KEY_CIPHER_FIELD ) Optional<CipherDescription> keyCipher,
            @JsonProperty( DATA_CIPHER_FIELD ) Optional<CipherDescription> dataCipher,
            @JsonProperty( PUBLIC_KEY_ALGORITHM_FIELD ) Optional<PublicKeyAlgorithm> publicKeyAlgorithm,
            @JsonProperty( PUBLIC_KEY_SIZE ) Optional<Integer> publicKeySize ) {
        this.keyCipher = Cypher.createCipher( keyCipher.or( Cypher.AES_CTR_128.getCipherDescription() ) );
        this.dataCipher = Cypher.createCipher( dataCipher.or( Cypher.RSA_OAEP_SHA256_2048.getCipherDescription() ) );
        this.publicKeyAlgorithm = publicKeyAlgorithm.or( PublicKeyAlgorithm.RSA );
        this.publicKeySize = publicKeySize.or( 2048 );
    }

    @JsonProperty( PUBLIC_KEY_SIZE )
    public int getPublicKeySize() {
        return publicKeySize;
    }

    @JsonProperty( KEY_CIPHER_FIELD )
    public Cypher getKeyCipher() {
        return keyCipher;
    }

    @JsonProperty( DATA_CIPHER_FIELD )
    public Cypher getDataCipher() {
        return dataCipher;
    }

    @JsonProperty( PUBLIC_KEY_ALGORITHM_FIELD )
    public PublicKeyAlgorithm getPublicKeyAlgorithm() {
        return publicKeyAlgorithm;
    }
}
