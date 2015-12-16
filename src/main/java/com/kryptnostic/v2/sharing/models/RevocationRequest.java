package com.kryptnostic.v2.sharing.models;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public class RevocationRequest {
    private final VersionedObjectKey objectId;
    private final Set<UUID>          userKeys;

    @JsonCreator
    public RevocationRequest(
            @JsonProperty( Names.ID_FIELD ) VersionedObjectKey objectId,
            @JsonProperty( Names.USERS_FIELD ) Set<UUID> userKeys ) {
        this.objectId = objectId;
        this.userKeys = userKeys;
    }

    @JsonProperty( Names.ID_FIELD )
    public VersionedObjectKey getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Set<UUID> getUserKeys() {
        return userKeys;
    }
}
