package com.kryptnostic.api.v1.indexing;

import java.util.Set;

import com.kryptnostic.api.v1.indexing.metadata.Metadata;
import com.kryptnostic.api.v1.indexing.metadata.Metadatum;

/**
 * MetadataKeyService handles mapping tokens and nonces to lookup keys.
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public interface MetadataKeyService {
	Metadata mapTokensToKeys( Set<Metadatum> metadata ); 
}
