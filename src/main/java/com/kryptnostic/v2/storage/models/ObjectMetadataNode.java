package com.kryptnostic.v2.storage.models;

import java.util.Map;
import java.util.UUID;

public class ObjectMetadataNode {
    private final Map<UUID, ObjectMetadataEncryptedNode> children;
    private final ObjectMetadata                         metadata;
    private final Object                                 data;

    public ObjectMetadataNode( ObjectMetadata metadata, Object data, Map<UUID, ObjectMetadataEncryptedNode> children ) {
        this.metadata = metadata;
        this.data = data;
        this.children = children;
    }

    public Map<UUID, ObjectMetadataEncryptedNode> getChildren() {
        return children;
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public Object getData() {
        return data;
    }

}
