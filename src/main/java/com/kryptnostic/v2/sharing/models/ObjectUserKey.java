package com.kryptnostic.v2.sharing.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectUserKey {
    private final UUID         objectId;
    private final UUID         userKey;

    @JsonCreator
    public ObjectUserKey(
            @JsonProperty( Names.ID_FIELD ) UUID objectId,
            @JsonProperty( Names.USER_FIELD ) UUID userKey) {
        super();
        this.objectId = objectId;
        this.userKey = userKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUserId() {
        return userKey;
    }

}
