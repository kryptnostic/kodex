package com.kryptnostic.heracles.registration.v1.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.directory.v1.models.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class RequestUpdateResponse {
    private final String            reason;
    private final boolean           successful;
    private final Optional<UserKey> user;

    public RequestUpdateResponse( boolean success, Optional<String> reason ) {
        this( success, reason, Optional.<UserKey> absent() );
    }

    @JsonCreator
    public RequestUpdateResponse(
            @JsonProperty( Names.SUCCESS_FIELD ) boolean success,
            @JsonProperty( Names.REASON_FIELD ) Optional<String> reason,
            @JsonProperty( Names.USER_FIELD ) Optional<UserKey> user ) {
        this.successful = success;
        this.reason = reason.orNull();
        this.user = user;
    }

    @JsonProperty( Names.REASON_FIELD )
    public String getReason() {
        return reason;
    }

    @JsonProperty( Names.SUCCESS_FIELD )
    public boolean isSuccessful() {
        return successful;
    }

    @JsonProperty( Names.USER_FIELD )
    public Optional<UserKey> getUser() {
        return user;
    }
}
