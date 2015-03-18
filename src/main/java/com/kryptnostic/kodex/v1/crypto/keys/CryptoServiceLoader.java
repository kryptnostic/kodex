package com.kryptnostic.kodex.v1.crypto.keys;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;

public interface CryptoServiceLoader {

    CryptoService get( String id ) throws ExecutionException;

    void put( String id, CryptoService service ) throws ExecutionException;

    Map<String, CryptoService> getAll( Set<String> ids ) throws ExecutionException;

    void clear();
}
