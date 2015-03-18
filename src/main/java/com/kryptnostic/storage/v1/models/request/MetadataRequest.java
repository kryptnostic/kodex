package com.kryptnostic.storage.v1.models.request;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.storage.v1.models.IndexedMetadata;

public class MetadataRequest {
    private static final String METADATA = "metadata";

    private final Collection<IndexedMetadata> metadata;

    @JsonCreator
    public MetadataRequest(@JsonProperty(METADATA) Collection<IndexedMetadata> metadata) {
        this.metadata = metadata;
    }

    @JsonProperty(METADATA)
    public Collection<IndexedMetadata> getMetadata() {
        return Collections.unmodifiableCollection(metadata);
    }
}
