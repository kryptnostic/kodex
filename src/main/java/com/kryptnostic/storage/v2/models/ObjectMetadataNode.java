package com.kryptnostic.storage.v2.models;

import java.util.Map;
import java.util.UUID;

public class ObjectMetadataNode {
    Map<UUID, ObjectMetadataNode> children;
    ObjectMetadata            metadata;
}
