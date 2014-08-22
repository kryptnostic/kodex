package com.kryptnostic.api.v1.search;

import java.util.Set;

public interface QueryAnalyzer {
    Set<String> analyzeQuery( String query );
}

