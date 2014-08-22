package com.kryptnostic.api.v1.search;

import cern.colt.bitvector.BitVector;


/**
 * Factory interface for classes capable of building searchers.
 * @author Matthew Tamayo-Rios <matthew@kryptnostic.com>
 */
public interface DocumentSearcherFactory {
	BitVector createSearchToken( String token );
}
