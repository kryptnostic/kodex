package com.kryptnostic.directory.v1.models.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectAppendNotification extends Notification {

    private final UserKey user;
    private final String  objectId;

    @JsonCreator
    public ObjectAppendNotification(
            @JsonProperty( Names.USER_FIELD ) UserKey user,
            @JsonProperty( Names.ID_FIELD ) String objectId ) {
        super( ObjectAppendNotification.class.getSimpleName() );
        this.user = user;
        this.objectId = objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UserKey getUser() {
        return user;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

}
