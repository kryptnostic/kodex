package com.kryptnostic.crypto.v1.keys;

import java.io.IOException;

public interface KodexMarshaller<T> {
    T fromBytes( byte[] bytes ) throws IOException;
    byte[] toBytes(T object) throws IOException;
}
