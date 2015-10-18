package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectAppendNotification extends Notification {

    private final UUID user;
    private final UUID objectId;

    @JsonCreator
    public ObjectAppendNotification(
            @JsonProperty( Names.USER_FIELD ) UUID user,
            @JsonProperty( Names.ID_FIELD ) UUID objectId ) {
        super( ObjectAppendNotification.class.getSimpleName() );
        this.user = user;
        this.objectId = objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUser() {
        return user;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

}
