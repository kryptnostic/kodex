package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentCreationRequest {

    private static final String FIELD_VERIFICATION = "verification";
    private final String verificationHash;

    @JsonCreator
    public DocumentCreationRequest(@JsonProperty(FIELD_VERIFICATION) String verificationHash) {
        this.verificationHash = verificationHash;
    }

    @JsonProperty(FIELD_VERIFICATION)
    public String getVerificationHash() {
        return verificationHash;
    }
}
