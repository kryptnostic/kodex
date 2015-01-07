package com.kryptnostic.kodex.v1.models.blocks;

import java.io.IOException;

/**
 * Interface for various approaches to turning objects into multiple blocks. Designed to allow for lazy evaluated
 * iterables for handling large objects.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public interface ChunkingStrategy {
    /**
     * 
     * @param object A java object representation of data
     * @return A collection of byte arrays that represent chunked blocks of plaintext data
     * @throws IOException
     */
    <T> Iterable<byte[]> split( T object ) throws IOException;

    /**
     * @param decryptedBlocks Collection of byte arrays representing chunked plaintext data
     * @param klass String representation of target class
     * @return A new object constructed from the plaintext blocks
     * @throws IOException
     */
    <T> T join( Iterable<byte[]> decryptedBlocks, Class<T> klass ) throws IOException;

    /**
     * @return Max length of each block
     */
    int getLength();
}
