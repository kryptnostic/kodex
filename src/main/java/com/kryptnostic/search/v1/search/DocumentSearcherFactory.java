package com.kryptnostic.search.v1.search;

import cern.colt.bitvector.BitVector;


/**
 * Factory interface for classes capable of building searchers.
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 */
public interface DocumentSearcherFactory {
	BitVector createSearchToken( String token );
}
