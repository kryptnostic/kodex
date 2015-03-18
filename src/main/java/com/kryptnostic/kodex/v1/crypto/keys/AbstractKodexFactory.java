package com.kryptnostic.kodex.v1.crypto.keys;

import com.google.common.base.Preconditions;

public abstract class AbstractKodexFactory<T> implements KodexMarshaller<T> {
    public String getName( T object ) {
        return Preconditions.checkNotNull( object, "Cannot get name of null object." ).getClass().getName();
    }
}
