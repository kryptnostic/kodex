package com.kryptnostic.v2.crypto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.kodex.v1.crypto.ciphers.Cypher;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public interface CryptoServiceLoader {

    @Nonnull
    Optional<CryptoService> get( VersionedObjectKey id ) throws ExecutionException;

    @Nonnull
    Optional<CryptoService> getLatest( UUID id ) throws ExecutionException;

    void put( VersionedObjectKey id, CryptoService service ) throws ExecutionException;

    @Nonnull
    Map<VersionedObjectKey, CryptoService> getAll( Set<VersionedObjectKey> ids ) throws ExecutionException;

    void clear();

    Cypher getCypher();
}
