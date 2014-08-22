package com.kryptnostic.api.v1.models.request;

import java.util.Collection;

import cern.colt.bitvector.BitVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

public class UserNonceUploadRequest {
    private static final String NONCES_PROPERTY = "nonces";
    private final Collection<BitVector> nonces;

    @JsonCreator
    public UserNonceUploadRequest(@JsonProperty(NONCES_PROPERTY) Collection<BitVector> nonces) {
        this.nonces = nonces;
    }

    @JsonProperty(NONCES_PROPERTY)
    public Collection<BitVector> getNonces() {
        return nonces;
    }
}
