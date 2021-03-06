package com.kryptnostic.sharing.v1.models.request;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class RevocationRequest {
    private final String       objectId;
    private final Set<UUID> userKeys;

    @JsonCreator
    public RevocationRequest(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.USERS_FIELD ) Set<UUID> userKeys ) {
        this.objectId = objectId;
        this.userKeys = userKeys;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Set<UUID> getUserKeys() {
        return userKeys;
    }
}
