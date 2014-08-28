package com.kryptnostic.storage.v1.models.request;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataRequest {
    private static final String METADATA = "metadata";

    private final Collection<IndexableMetadata> metadata;

    @JsonCreator
    public MetadataRequest(@JsonProperty(METADATA) Collection<IndexableMetadata> metadata) {
        this.metadata = metadata;
    }

    @JsonProperty(METADATA)
    public Collection<IndexableMetadata> getMetadata() {
        return Collections.unmodifiableCollection(metadata);
    }
}
