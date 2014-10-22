package com.kryptnostic.crypto.v1.keys;

import com.google.common.base.Preconditions;

public abstract class AbstractKodexFactory<T> implements KodexFactory<T> {
    @Override
    public String getName(T object) {
        return Preconditions.checkNotNull( object , "Cannot get name of null object.").getClass().getName();
    }
}
