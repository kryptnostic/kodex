package com.kryptnostic.kodex.v1.storage;

import java.io.IOException;

public interface DataStore {
    /**
     * Retrieves data previously stored with key, null if there is no data corresponding to this key.
     * @param   key     the unique identifier for the object to be retrieved.
     * @return          the data stored at this location, or null if there is none.
     */
    byte[] get(byte[] key) throws IOException;
    /**
     * Stores data at the location specified by key.
     * @param   key     the unique identifier for the object to be stored.
     * @param   value   the data to be stored.
     */
    void put(byte[] key, byte[] value) throws IOException;
}
