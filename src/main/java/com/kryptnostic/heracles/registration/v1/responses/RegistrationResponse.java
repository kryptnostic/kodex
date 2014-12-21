package com.kryptnostic.heracles.registration.v1.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.heracles.registration.v1.requests.RegistrationResult;
import com.kryptnostic.kodex.v1.constants.Names;

public final class RegistrationResponse {
    private final String email;
    private final RegistrationResult status;
    private final String reason;
    
    public RegistrationResponse(
            @JsonProperty(Names.EMAIL_FIELD) String email,
            @JsonProperty(Names.STATUS_FIELD) RegistrationResult status,
            @JsonProperty(Names.REASON_FIELD) Optional<String> reason
            ) {
        this.email = email;
        this.status = status;
        this.reason = reason.orNull();
    }
    
    @JsonProperty(Names.EMAIL_FIELD) 
    public String getEmail() {
        return email;
    }
    
    @JsonProperty(Names.STATUS_FIELD) 
    public RegistrationResult getStatus() {
        return status;
    }

    @JsonProperty(Names.REASON_FIELD) 
    public String getReason() {
        return reason;
    }
}
