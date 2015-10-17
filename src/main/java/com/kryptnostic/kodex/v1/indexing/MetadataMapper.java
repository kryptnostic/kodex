package com.kryptnostic.kodex.v1.indexing;

import java.util.Collection;
import java.util.Set;

import com.kryptnostic.indexing.v1.PaddedMetadata;
import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;

/**
 * MetadataMapper handles mapping tokens and nonces to lookup keys.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface MetadataMapper {

    Collection<PaddedMetadata> mapTokensToKeys( Set<Metadata> metadata, byte[] objectIndexPair ) throws IrisException;
}
