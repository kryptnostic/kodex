package com.kryptnostic.kodex.v1.crypto.keys;

import java.util.concurrent.ExecutionException;

import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;

public interface CryptoServiceLoader {

    CryptoService get( String id ) throws ExecutionException;

    void put( String id, CryptoService service ) throws ExecutionException;
}
