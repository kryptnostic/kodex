package com.kryptnostic.kodex.v1.crypto.keys;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;

public interface CryptoServiceLoader<K> {

    Optional<CryptoService> get( K id ) throws ExecutionException;

    void put( K id, CryptoService service ) throws ExecutionException;

    Map<K, CryptoService> getAll( Set<K> ids ) throws ExecutionException;

    void clear();
}
