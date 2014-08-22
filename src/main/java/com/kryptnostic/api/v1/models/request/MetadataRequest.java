package com.kryptnostic.api.v1.models.request;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.api.v1.models.IndexableMetadata;

public class MetadataRequest {
    private static final String METADATA_PROPERTY = "metadata";

    private final Collection<IndexableMetadata> metadata;

    @JsonCreator
    public MetadataRequest(@JsonProperty(METADATA_PROPERTY) Collection<IndexableMetadata> metadata) {
        this.metadata = metadata;
    }

    @JsonProperty(METADATA_PROPERTY)
    public Collection<IndexableMetadata> getMetadata() {
        return Collections.unmodifiableCollection(metadata);
    }
}
