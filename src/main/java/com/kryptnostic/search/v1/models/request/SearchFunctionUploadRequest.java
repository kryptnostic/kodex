package com.kryptnostic.search.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class SearchFunctionUploadRequest {

    private static final String FUNCTION_PROPERTY = "function";
    private final SimplePolynomialFunction function;

    @JsonCreator
    public SearchFunctionUploadRequest(@JsonProperty(FUNCTION_PROPERTY) SimplePolynomialFunction function) {
        this.function = function;
    }

    @JsonProperty(FUNCTION_PROPERTY)
    public SimplePolynomialFunction getFunction() {
        return function;
    }
}
