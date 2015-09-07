package com.kryptnostic.directory.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;

public class ContactDiscoveryRequest {
    private final Optional<String> email;
    private final String           term;

    @JsonCreator
    public ContactDiscoveryRequest(
            @JsonProperty( Names.EMAIL_FIELD ) Optional<String> email,
            @JsonProperty( Names.QUERY_FIELD ) String term ) {
        this.email = email;
        this.term = term;
    }

    @JsonProperty( Names.EMAIL_FIELD )
    public Optional<String> getEmail() {
        return email;
    }

    @JsonProperty( Names.QUERY_FIELD )
    public String getTerm() {
        return term;
    }
}
