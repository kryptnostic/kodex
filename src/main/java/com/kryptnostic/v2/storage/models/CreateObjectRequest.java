package com.kryptnostic.v2.storage.models;

import java.util.EnumSet;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.ObjectMetadata.CryptoMaterial;
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
    private final EnumSet<CryptoMaterial>      requiredCryptoMats;

    public CreateObjectRequest() {
        this(
                TypeUUIDs.DEFAULT_TYPE,
                Optional.<VersionedObjectKey> absent(),
                Optional.<VersionedObjectKey> absent(),
                CryptoMaterial.DEFAULT_REQUIRED_CRYPTO_MATERIALS,
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent() );
    }

    @JsonCreator
    public CreateObjectRequest(
            @JsonProperty( Names.TYPE_FIELD ) UUID type,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<VersionedObjectKey> parentObjectId,
            @JsonProperty( Names.ID_FIELD ) Optional<VersionedObjectKey> objectId,
            @JsonProperty( Names.REQUIRED_CRYPTO_MATS_FIELD ) EnumSet<CryptoMaterial> requiredCryptoMaterial,
            @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership,
            @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD ) Optional<Boolean> inheritCryptoService,
            @JsonProperty( Names.LOCKED_FIELD ) Optional<Boolean> locked) {
        this.type = type;
        this.parentObjectId = parentObjectId;
        this.objectId = objectId;
        this.inheritingOwnership = inheritOwnership.or( INHERITING_OWNERSHIP_DEFAULT );
        this.inheritingCryptoService = inheritCryptoService.or( INHERITING_CRYPTO_SERVICE_DEFAULT );
        this.locked = locked.or( LOCKED_OBJECT_DEFAULT );
        this.requiredCryptoMats = requiredCryptoMaterial;
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

    @JsonProperty( Names.REQUIRED_CRYPTO_MATS_FIELD )
    public EnumSet<CryptoMaterial> getRequiredCryptoMaterials() {
        return requiredCryptoMats;
    }

    public boolean isLocked() {
        return locked;
    }

    public static class Builder {
        private UUID                         type                 = TypeUUIDs.DEFAULT_TYPE;
        private Optional<VersionedObjectKey> parentObjectkey      = Optional.<VersionedObjectKey> absent();
        private Optional<VersionedObjectKey> objectId             = Optional.<VersionedObjectKey> absent();
        private Optional<Boolean>            inheritOwnership     = Optional.<Boolean> absent();
        private Optional<Boolean>            inheritCryptoService = Optional.<Boolean> absent();
        private Optional<Boolean>            locked               = Optional.<Boolean> absent();
        private EnumSet<CryptoMaterial>      cryptomaterial       = CryptoMaterial.DEFAULT_REQUIRED_CRYPTO_MATERIALS;

        public Builder setType( UUID type ) {
            this.type = type;
            return this;
        }

        public Builder setParentObjectId( VersionedObjectKey parentObjectId ) {
            this.parentObjectkey = Optional.<VersionedObjectKey> of( parentObjectId );
            return this;
        }

        public Builder setObjectId( VersionedObjectKey objectId ) {
            this.objectId = Optional.<VersionedObjectKey> of( objectId );
            return this;
        }

        public Builder setType( EnumSet<CryptoMaterial> cryptoMaterials ) {
            this.cryptomaterial = cryptoMaterials;
            return this;
        }

        public Builder setInheritOwnership( Boolean inheritOwnership ) {
            this.inheritOwnership = Optional.<Boolean> of( inheritOwnership );
            return this;
        }

        public Builder setInheritCryptoService( Boolean inheritCryptoService ) {
            this.inheritCryptoService = Optional.<Boolean> of( inheritCryptoService );
            return this;
        }

        public Builder setLocked( Boolean locked ) {
            this.locked = Optional.<Boolean> of( locked );
            return this;
        }

        public CreateObjectRequest createCreateObjectRequest() {
            return new CreateObjectRequest(
                    type,
                    parentObjectkey,
                    objectId,
                    cryptomaterial,
                    inheritOwnership,
                    inheritCryptoService,
                    locked );
        }
    }

}
