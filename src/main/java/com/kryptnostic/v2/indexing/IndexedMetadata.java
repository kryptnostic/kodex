package com.kryptnostic.v2.indexing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.storage.v2.models.VersionedObjectKey;

public class IndexedMetadata {
    private final byte[]             key;
    private final VersionedObjectKey metadataObjectKey;
    private final VersionedObjectKey objectId;

    @JsonCreator
    public IndexedMetadata(
            @JsonProperty( Names.KEY_FIELD ) byte[] key,
            @JsonProperty( Names.METADATA_FIELD ) VersionedObjectKey metadataObjectKey,
            @JsonProperty( Names.ID_FIELD ) VersionedObjectKey objectId ) {
        this.key = key;
        this.metadataObjectKey = metadataObjectKey;
        this.objectId = objectId;
    }

    @JsonProperty( Names.KEY_FIELD )
    public byte[] getKey() {
        return key;
    }

    @JsonProperty( Names.METADATA_FIELD )
    public VersionedObjectKey getMetadataObjectKey() {
        return metadataObjectKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public VersionedObjectKey getObjectId() {
        return objectId;
    }

}
