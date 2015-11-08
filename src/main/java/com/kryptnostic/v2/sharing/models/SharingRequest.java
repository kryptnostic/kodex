package com.kryptnostic.v2.sharing.models;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public final class SharingRequest implements Serializable {
    private static final long               serialVersionUID = 8493560981719181963L;
    private final Map<UUID, byte[]>         rsaEncryptedCryptoServices;
    private final VersionedObjectKey                    objectId;
    private final Optional<BlockCiphertext> encryptedSharingPair;

    public SharingRequest(
            VersionedObjectKey objectId,
            Map<UUID, byte[]> rsaEncryptedCryptoServices,
            Optional<BlockCiphertext> encryptedSharingPair ) {
        this( objectId, Optional.<String> absent(), rsaEncryptedCryptoServices, encryptedSharingPair );
    }

    @JsonCreator
    @JsonIgnoreProperties(
        ignoreUnknown = true )
    public SharingRequest(
            @JsonProperty( Names.ID_FIELD ) VersionedObjectKey objectId,
            @JsonProperty( Names.SHARING_KEY ) Optional<String> sharingKey,
            @JsonProperty( Names.USERS_FIELD ) Map<UUID, byte[]> rsaEncryptedCryptoServices,
            @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD ) Optional<BlockCiphertext> encryptedSharingPair ) {
        this.objectId = objectId;
        this.rsaEncryptedCryptoServices = rsaEncryptedCryptoServices;
        this.encryptedSharingPair = encryptedSharingPair;
    }

    @JsonProperty( Names.ID_FIELD )
    public VersionedObjectKey getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USERS_FIELD )
    public Map<UUID, byte[]> getUserKeys() {
        return rsaEncryptedCryptoServices;
    }

    @JsonProperty( Names.OBJECT_SHARING_PAIR_FIELD )
    public Optional<BlockCiphertext> getEncryptedSharingPair() {
        return encryptedSharingPair;
    }
}
