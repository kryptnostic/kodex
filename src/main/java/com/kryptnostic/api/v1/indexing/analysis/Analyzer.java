package com.kryptnostic.api.v1.indexing.analysis;

import java.util.List;
import java.util.Map;

public interface Analyzer {
    /**
     * @return Map<String,List<Integer>>, a Map of tokens to a List of start indices for that token in the source
     *         String.
     */
    Map<String, List<Integer>> analyze(String source);
}
