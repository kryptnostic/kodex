package com.kryptnostic.directory.v1.models.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class Notification {

    private final String type;

    @JsonCreator
    public Notification( @JsonProperty( Names.TYPE_FIELD ) String type ) {
        this.type = type;
    }

    @JsonProperty( Names.TYPE_FIELD )
    public String getType() {
        return type;
    }

}
