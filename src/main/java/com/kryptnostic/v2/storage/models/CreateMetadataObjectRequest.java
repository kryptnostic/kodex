package com.kryptnostic.v2.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.BlockCiphertext;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.types.TypeUUIDs;

public class CreateMetadataObjectRequest extends CreateObjectRequest {
    private final byte[]           address;
    private final Optional<Double> score;

    public CreateMetadataObjectRequest( byte[] address ) {
        super(
                TypeUUIDs.INDEX_METADATA,
                Optional.<VersionedObjectKey> absent(),
                Optional.<VersionedObjectKey> absent(),
                Cypher.DEFAULT,
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent(),
                Optional.<Boolean> absent(),
                Optional.<BlockCiphertext> absent() );
        this.address = address;
        this.score = Optional.absent();
    }

    @JsonCreator
    public CreateMetadataObjectRequest(
            @JsonProperty( Names.ADDRESS_FIELD ) byte[] address,
            @JsonProperty( Names.PARENT_OBJECT_ID_FIELD ) Optional<VersionedObjectKey> parentObjectId,
            @JsonProperty( Names.ID_FIELD ) Optional<VersionedObjectKey> objectId,
            @JsonProperty( Names.SCORE_FIELD ) Optional<Double> score,
            @JsonProperty( Names.INHERITING_OWNERSHIP_FIELD ) Optional<Boolean> inheritOwnership,
            @JsonProperty( Names.INHERITING_CRYPTO_SERVICE_FIELD ) Optional<Boolean> inheritCryptoService,
            @JsonProperty( Names.LOCKED_FIELD ) Optional<Boolean> locked,
            @JsonProperty( Names.BLOCK_CIPHERTEXT ) Optional<BlockCiphertext> contents) {
        super(
                TypeUUIDs.INDEX_METADATA,
                parentObjectId,
                objectId,
                Cypher.DEFAULT,
                inheritOwnership,
                inheritCryptoService,
                locked,
                contents );
        this.address = address;
        this.score = score;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public byte[] getAddress() {
        return address;
    }

    @JsonProperty( Names.SCORE_FIELD )
    public Optional<Double> getScore() {
        return score;
    }
}
