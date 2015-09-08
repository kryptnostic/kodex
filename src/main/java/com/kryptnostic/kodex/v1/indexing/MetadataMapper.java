package com.kryptnostic.kodex.v1.indexing;

import java.util.Set;

import com.kryptnostic.kodex.v1.exceptions.types.IrisException;
import com.kryptnostic.kodex.v1.indexing.metadata.MappedMetadata;
import com.kryptnostic.kodex.v1.indexing.metadata.Metadata;

/**
 * MetadataMapper handles mapping tokens and nonces to lookup keys.
 * 
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface MetadataMapper {

    MappedMetadata mapTokensToKeys(
            Set<Metadata> metadata,
            byte[] objectSearchKey, byte[] objectAddressMatrix ) throws IrisException;
}
