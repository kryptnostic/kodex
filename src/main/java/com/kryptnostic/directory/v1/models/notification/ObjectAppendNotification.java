package com.kryptnostic.directory.v1.models.notification;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectAppendNotification extends Notification {

    private final UUID user;
    private final String  objectId;

    @JsonCreator
    public ObjectAppendNotification(
            @JsonProperty( Names.USER_FIELD ) UUID user,
            @JsonProperty( Names.ID_FIELD ) String objectId ) {
        super( ObjectAppendNotification.class.getSimpleName() );
        this.user = user;
        this.objectId = objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUser() {
        return user;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

}
