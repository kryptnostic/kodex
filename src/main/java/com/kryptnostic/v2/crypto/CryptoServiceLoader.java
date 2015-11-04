package com.kryptnostic.v2.crypto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;

public interface CryptoServiceLoader {

    Optional<CryptoService> get( UUID id ) throws ExecutionException;

    void put( UUID id, CryptoService service ) throws ExecutionException;

    Map<UUID, CryptoService> getAll( Set<UUID> ids ) throws ExecutionException;

    void clear();
}
