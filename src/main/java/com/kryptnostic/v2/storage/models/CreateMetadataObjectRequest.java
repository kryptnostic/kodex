package com.kryptnostic.v2.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.ObjectMetadata.CryptoMaterial;
import com.kryptnostic.v2.storage.types.TypeUUIDs;

public class CreateMetadataObjectRequest extends CreateObjectRequest {
    private final byte[] address;

    public CreateMetadataObjectRequest( byte[] address ) {
        super(
                TypeUUIDs.INDEX_METADATA,
                Optional.<VersionedObjectKey> absent(),
                Optional.<VersionedObjectKey> absent(),
                CryptoMaterial.DEFAULT_REQUIRED_CRYPTO_MATERIALS,
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent() );
        this.address = address;
    }

    @JsonCreator
    public CreateMetadataObjectRequest(
            @JsonProperty( Names.ADDRESS_FIELD ) byte[] address,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<VersionedObjectKey> parentObjectId,
            @JsonProperty( Names.ID_FIELD ) Optional<VersionedObjectKey> objectId,
            @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership,
            @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD ) Optional<Boolean> inheritCryptoService,
            @JsonProperty( Names.LOCKED_FIELD ) Optional<Boolean> locked) {
        super(
                TypeUUIDs.INDEX_METADATA,
                parentObjectId,
                objectId,
                CryptoMaterial.DEFAULT_REQUIRED_CRYPTO_MATERIALS,
                inheritOwnership,
                inheritCryptoService,
                locked );
        this.address = address;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public byte[] getAddress() {
        return address;
    }
}
