package com.kryptnostic.v2.indexing.metadata;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.indexing.IndexMetadata;

public class MetadataRequest {
    private final Collection<IndexMetadata> metadata;

    @JsonCreator
    public MetadataRequest( @JsonProperty( Names.METADATA_FIELD ) Collection<IndexMetadata> metadata ) {
        this.metadata = metadata;
    }

    @JsonProperty( Names.METADATA_FIELD )
    public Collection<IndexMetadata> getMetadata() {
        return Collections.unmodifiableCollection( metadata );
    }
}
