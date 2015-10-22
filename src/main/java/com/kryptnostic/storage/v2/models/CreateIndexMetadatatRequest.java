package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.storage.v2.types.TypeUUIDs;
import com.kryptnostic.v2.constants.Names;

public class CreateIndexMetadatatRequest {
    private static final boolean INHERITING_OWNERSHIP_DEFAULT      = true;
    private static final boolean INHERITING_CRYPTO_SERVICE_DEFAULT = true;

    private final UUID           type;
    private final Optional<UUID> parentObjectId;
    private final boolean        inheritingOwnership;
    private final boolean        inheritingCryptoService;

    public CreateIndexMetadatatRequest() {
        this(
                Optional.<UUID> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent() );
    }

    @JsonCreator
    public CreateIndexMetadatatRequest(
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<UUID> parentObjectId,
            @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership,
            @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD ) Optional<Boolean> inheritCryptoService ) {
        this.type = TypeUUIDs.METADATA;
        this.parentObjectId = parentObjectId;
        this.inheritingOwnership = inheritOwnership.or( INHERITING_OWNERSHIP_DEFAULT );
        this.inheritingCryptoService = inheritCryptoService.or( INHERITING_CRYPTO_SERVICE_DEFAULT );
    }

    @JsonProperty( Names.TYPE_FIELD )
    public UUID getType() {
        return type;
    }

    @JsonProperty( Names.PARENT_OBJECT_ID_FIELD )
    public Optional<UUID> getParentId() {
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

}
