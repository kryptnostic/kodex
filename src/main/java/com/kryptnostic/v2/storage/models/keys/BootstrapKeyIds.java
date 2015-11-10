package com.kryptnostic.v2.storage.models.keys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public class BootstrapKeyIds {
    private final Optional<VersionedObjectKey> fhePrivateKey;
    private final Optional<VersionedObjectKey> fheSearchPrivateKey;
    private final Optional<VersionedObjectKey> clientHashFunction;

    public BootstrapKeyIds(
            @JsonProperty( Names.FHE_PRIVATE_KEY ) Optional<VersionedObjectKey> fhePrivateKey,
            @JsonProperty( Names.FHE_SEARCH_PRIVATE_KEY ) Optional<VersionedObjectKey> fheSearchPrivateKey,
            @JsonProperty( Names.CLIENT_HASH_FUNCTION ) Optional<VersionedObjectKey> clientHashFunction ) {
        this.fhePrivateKey = fhePrivateKey;
        this.fheSearchPrivateKey = fheSearchPrivateKey;
        this.clientHashFunction = clientHashFunction;
    }

    @JsonProperty( Names.FHE_PRIVATE_KEY )
    public Optional<VersionedObjectKey> getFhePrivateKey() {
        return fhePrivateKey;
    }

    @JsonProperty( Names.FHE_SEARCH_PRIVATE_KEY )
    public Optional<VersionedObjectKey> getFheSearchPrivateKey() {
        return fheSearchPrivateKey;
    }

    @JsonProperty( Names.CLIENT_HASH_FUNCTION ) 
    public Optional<VersionedObjectKey> getClientHashFunction() {
        return clientHashFunction;
    }
}
