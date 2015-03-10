package com.kryptnostic.sharing.v1.models.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class RevocationRequest {
    private final String       objectId;
    private final Set<UserKey> userKeys;

    @JsonCreator
    public RevocationRequest(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.USERS_FIELD ) Set<UserKey> userKeys ) {
        this.objectId = objectId;
        this.userKeys = userKeys;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Set<UserKey> getUserKeys() {
        return userKeys;
    }
}
