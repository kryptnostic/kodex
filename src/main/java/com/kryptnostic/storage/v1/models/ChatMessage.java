package com.kryptnostic.storage.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ChatMessage {
    private final String user;
    private final String message;

    @JsonCreator
    public ChatMessage( @JsonProperty( Names.USER_FIELD ) String user, @JsonProperty( Names.DATA_FIELD ) String message ) {
        this.user = user;
        this.message = message;
    }

    @JsonProperty( Names.USER_FIELD )
    public String getUser() {
        return user;
    }

    @JsonProperty( Names.DATA_FIELD )
    public String getMessage() {
        return message;
    }

}
