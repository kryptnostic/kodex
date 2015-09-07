package com.kryptnostic.sharing.v1.models.request;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class SharingRequest implements Serializable {
    private static final long          serialVersionUID = 8493560981719181963L;
    private final Map<UUID, byte[]> userKeys;
    private final String               objectId;
    private final byte[]               encryptedSharingKey;

    @JsonCreator
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.USERS_FIELD ) Map<UUID, byte[]> userKey,
            @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD ) byte[] encryptedSharingKey ) {
        this.objectId = objectId;
        this.userKeys = userKey;
        this.encryptedSharingKey = encryptedSharingKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Map<UUID, byte[]> getUserKeys() {
        return userKeys;
    }

    @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD )
    public byte[] getEncryptedSharingKey() {
        return encryptedSharingKey;
    }
}
