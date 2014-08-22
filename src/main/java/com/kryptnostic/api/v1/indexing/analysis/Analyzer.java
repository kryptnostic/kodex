package com.kryptnostic.api.v1.indexing.analysis;

import java.util.List;
import java.util.Map;


public interface Analyzer {
	Map<String, List<Integer>> analyze( String source );
}
