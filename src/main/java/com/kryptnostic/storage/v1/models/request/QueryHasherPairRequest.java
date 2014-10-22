package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

public class QueryHasherPairRequest {

    public static final String FIELD_LEFT = "left";
    public static final String FIELD_RIGHT = "right";

    private final SimplePolynomialFunction left;
    private final SimplePolynomialFunction right;

    @JsonCreator
    public QueryHasherPairRequest(@JsonProperty(FIELD_LEFT) SimplePolynomialFunction left,
            @JsonProperty(FIELD_RIGHT) SimplePolynomialFunction right) {
        this.left = left;
        this.right = right;
    }

    @JsonProperty(FIELD_LEFT)
    public SimplePolynomialFunction getLeft() {
        return left;
    }

    @JsonProperty(FIELD_RIGHT)
    public SimplePolynomialFunction getRight() {
        return right;
    }

}
