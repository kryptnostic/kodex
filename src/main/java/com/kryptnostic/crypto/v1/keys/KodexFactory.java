package com.kryptnostic.crypto.v1.keys;

public interface KodexFactory<T> {
    T fromBytes( byte[] bytes );
    byte[] toBytes(T object);
    String getName( T object );
}
