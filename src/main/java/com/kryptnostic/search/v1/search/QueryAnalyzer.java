package com.kryptnostic.search.v1.search;

import java.util.Set;

public interface QueryAnalyzer {
    Set<String> analyzeQuery( String query );
}

