package com.kryptnostic.heracles.registration.v1.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public final class RequestUpdateRequest {
    private final RequestStatus status;
    private final String reason;
    
    @JsonCreator
    public RequestUpdateRequest( 
            @JsonProperty(Names.STATUS_FIELD) RequestStatus status,
            @JsonProperty(Names.REASON_FIELD) Optional<String> reason 
            ) {
        this.status = status;
        this.reason = reason.orNull();
    }
    
    @JsonProperty(Names.STATUS_FIELD)
    public RequestStatus getStatus() {
        return status;
    }

    @JsonProperty(Names.REASON_FIELD)
    public String getReason() {
        return reason;
    }
}
