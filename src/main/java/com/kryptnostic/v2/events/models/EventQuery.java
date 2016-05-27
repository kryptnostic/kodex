package com.kryptnostic.v2.events.models;

import com.google.common.base.Optional;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public class EventQuery {
    Optional<VersionedObjectKey> source;
    Optional<String>             sourceType;
    Optional<String>             eventType;
}
