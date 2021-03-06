package com.kryptnostic.kodex.v1.indexing.analysis;

import java.util.List;
import java.util.Map;

public interface Analyzer {
    /**
     * @param source
     * @return Map of tokens to List of index in document
     */
    Map<String, List<List<Integer>>> analyze(String source);
}
