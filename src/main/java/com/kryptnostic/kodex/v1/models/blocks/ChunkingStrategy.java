package com.kryptnostic.kodex.v1.models.blocks;

/**
 * Interface for various approaches to turning objects into multiple blocks.
 * Designed to allow for lazy evaluated iterables for handling large objects.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt; 
 */
public interface ChunkingStrategy {
    Iterable<byte[]> split( Object object );
    Object join( Iterable<byte[]> blocks );
}
