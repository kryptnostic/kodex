package com.kryptnostic.sharing.v1.models.request;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class SharingRequest implements Serializable {
    private static final long       serialVersionUID = 8493560981719181963L;
    private final Map<UUID, byte[]> rsaEncryptedCryptoServices;
    private final String            objectId;
    private final byte[]            encryptedSharingPair;

    @JsonCreator
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.USERS_FIELD ) Map<UUID, byte[]> rsaEncryptedCryptoServices,
            @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD ) byte[] encryptedSharingPair ) {
        this.objectId = objectId;
        this.rsaEncryptedCryptoServices = rsaEncryptedCryptoServices;
        this.encryptedSharingPair = encryptedSharingPair;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Map<UUID, byte[]> getUserKeys() {
        return rsaEncryptedCryptoServices;
    }

    @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD )
    public byte[] getEncryptedSharingPair() {
        return encryptedSharingPair;
    }
}
