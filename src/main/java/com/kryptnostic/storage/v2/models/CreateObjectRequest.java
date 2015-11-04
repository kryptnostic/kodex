package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.types.TypeUUIDs;

public class CreateObjectRequest {
    private static final boolean               INHERITING_OWNERSHIP_DEFAULT      = true;
    private static final boolean               INHERITING_CRYPTO_SERVICE_DEFAULT = true;
    private static final boolean               LOCKED_OBJECT_DEFAULT             = true;

    private final UUID                         type;
    private final Optional<VersionedObjectKey> objectId;
    private final Optional<VersionedObjectKey> parentObjectId;
    private final boolean                      inheritingOwnership;
    private final boolean                      inheritingCryptoService;
    private final boolean                      locked;

    public CreateObjectRequest() {
        this(
                TypeUUIDs.DEFAULT_TYPE,
                Optional.<VersionedObjectKey> absent(),
                Optional.<VersionedObjectKey> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent() );
    }

    @JsonCreator
    public CreateObjectRequest(
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<VersionedObjectKey> parentObjectId,
            @JsonProperty( Names.ID_FIELD ) Optional<VersionedObjectKey> objectId,
            @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership,
            @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD ) Optional<Boolean> inheritCryptoService,
            @JsonProperty( Names.LOCKED_FIELD ) Optional<Boolean> locked ) {
        this.type = type;
        this.parentObjectId = parentObjectId;
        this.objectId = objectId;
        this.inheritingOwnership = inheritOwnership.or( INHERITING_OWNERSHIP_DEFAULT );
        this.inheritingCryptoService = inheritCryptoService.or( INHERITING_CRYPTO_SERVICE_DEFAULT );
        this.locked = locked.or( LOCKED_OBJECT_DEFAULT );
    }

    @JsonProperty( Names.TYPE_FIELD )
    public UUID getType() {
        return type;
    }

    @JsonProperty( Names.ID_FIELD )
    public Optional<VersionedObjectKey> getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.PARENT_OBJECT_ID_FIELD )
    public Optional<VersionedObjectKey> getParentId() {
        return parentObjectId;
    }

    @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD )
    public boolean isInheritingOwnership() {
        return inheritingOwnership;
    }

    @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD )
    public boolean isInheritingCryptoService() {
        return inheritingCryptoService;
    }

    @JsonProperty( Names.LOCKED_FIELD )
    public boolean isLocked() {
        return locked;
    }
}
