package com.kryptnostic.crypto.v1.keys;

public interface KodexMarshaller<T> {
    T fromBytes( byte[] bytes );
    byte[] toBytes(T object);
}
