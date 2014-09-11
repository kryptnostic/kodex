package com.kryptnostic.heracles.v1.ciphers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CipherDescription {
    private static final String ALGORITHM_PROPERTY = "algorithm";
    private static final String MODE_PROPERTY = "mode";
    private static final String PADDING_PROPERTY = "padding";
    private static final String KEYSIZE_PROPERTY = "keySize";
    
    private final BlockCipher algorithm;
    private final Mode mode;
    private final Padding padding;
    private final int keySize;
    
    @JsonCreator
    public CipherDescription( 
            @JsonProperty(ALGORITHM_PROPERTY) BlockCipher algorithm , 
            @JsonProperty(MODE_PROPERTY) Mode mode , 
            @JsonProperty(PADDING_PROPERTY) Padding padding, 
            @JsonProperty(KEYSIZE_PROPERTY) int keySize ) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }

    @JsonProperty(ALGORITHM_PROPERTY) 
    public BlockCipher getAlgorithm() {
        return algorithm;
    }
    
    @JsonProperty(MODE_PROPERTY) 
    public Mode getMode() {
        return mode;
    }

    @JsonProperty(PADDING_PROPERTY)
    public Padding getPadding() {
        return padding;
    }

    @JsonProperty(KEYSIZE_PROPERTY) 
    public int getKeySize() {
        return keySize;
    }
}
