package com.kryptnostic.kodex.v1.crypto.keys;

import java.util.concurrent.ExecutionException;

import com.kryptnostic.kodex.v1.crypto.ciphers.CryptoService;
import com.kryptnostic.sharing.v1.models.DocumentId;

public interface CryptoServiceLoader {
    CryptoService get( DocumentId id ) throws ExecutionException;

    CryptoService get( String id ) throws ExecutionException;

    void put( String id, CryptoService service );
}
