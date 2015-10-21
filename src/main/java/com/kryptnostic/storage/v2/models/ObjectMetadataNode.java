package com.kryptnostic.storage.v2.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ObjectMetadataNode {
    Map<UUID, ObjectMetadataNode> children;
    ObjectMetadata            metadata;

    public ObjectMetadataNode( ObjectMetadata metadata ) {
        this.children = new HashMap<>();
        this.metadata = metadata;
    }
}
