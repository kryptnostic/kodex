package com.kryptnostic.kodex.v1.serialization.jackson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.kryptnostic.kodex.v1.crypto.keys.CryptoServiceLoader;


public class CryptoServiceLoaderHolder {
    private static final CryptoServiceLoaderHolder emptyHolder = new CryptoServiceLoaderHolder();
    private final Optional<CryptoServiceLoader> maybeLoader;
    private CryptoServiceLoaderHolder( @Nullable CryptoServiceLoader loader ) {
        this.maybeLoader = Optional.of( loader );
    }
    
    private CryptoServiceLoaderHolder() {
        maybeLoader = Optional.absent();
    }
    
    public boolean isPresent() {
        return maybeLoader.isPresent();
    }
    
    public static @Nonnull CryptoServiceLoaderHolder fromLoader( @Nonnull CryptoServiceLoader loader ) {
        return new CryptoServiceLoaderHolder( loader );
    }
    
    public static @Nonnull CryptoServiceLoaderHolder getEmptyHolder() {
        return emptyHolder;
    }

    public CryptoServiceLoader get() {
        return maybeLoader.get();
    }
}
