package com.kryptnostic.v2.indexing.metadata;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kryptnostic.kodex.v1.exceptions.types.IrisException;

/**
 * MetadataMapper handles mapping tokens and nonces to lookup keys.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface MetadataMapper {

    Map<ByteBuffer, List<Metadata>> mapTokensToKeys( Set<BucketedMetadata> metadata, byte[] objectIndexPair )
            throws IrisException;
}
