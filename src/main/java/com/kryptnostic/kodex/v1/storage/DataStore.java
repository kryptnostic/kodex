package com.kryptnostic.kodex.v1.storage;

import java.io.IOException;

public interface DataStore {
    /**
     * Retrieves data previously stored with key, null if there is no data corresponding to this key.
     * 
     * @param dir Directory. Can be null
     * @param file Must not be null
     * @return the data stored at this location, or null if there is none.
     * @throws IOException
     */
    byte[] get( String dir, String file ) throws IOException;

    /**
     * Retrieves data previously stored with key, null if there is no data corresponding to this key.
     * 
     * @param file Must not be null
     * @return the data stored at this location, or null if there is none.
     * @throws IOException
     */
    byte[] get( String file ) throws IOException;

    /**
     * Stores data at the location specified by key.
     * 
     * @param dir Directory. Can be null
     * @param file Must not be null
     * @param value the data to be stored.
     * @throws IOException
     */
    void put( String dir, String file, byte[] value ) throws IOException;

    /**
     * Stores data at the location specified by key.
     * 
     * @param file Must not be null
     * @param value the data to be stored.
     * @throws IOException
     */
    void put( String file, byte[] value ) throws IOException;

    /**
     * @param file
     * @throws IOException
     */
    void delete( String file ) throws IOException;

    void clear() throws IOException;
}
