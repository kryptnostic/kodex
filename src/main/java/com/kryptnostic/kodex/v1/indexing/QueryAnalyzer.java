package com.kryptnostic.kodex.v1.indexing;

import java.util.Set;

public interface QueryAnalyzer {
    Set<String> analyzeQuery( String query );
}

