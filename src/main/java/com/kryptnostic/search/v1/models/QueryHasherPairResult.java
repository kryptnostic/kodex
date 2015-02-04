package com.kryptnostic.search.v1.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;
import com.kryptnostic.linear.EnhancedBitMatrix;

public class QueryHasherPairResult {
    private final EnhancedBitMatrix left;
    private final EnhancedBitMatrix right;

    @JsonCreator
    public QueryHasherPairResult(
            @JsonProperty( Names.LEFT_FIELD ) EnhancedBitMatrix left,
            @JsonProperty( Names.RIGHT_FIELD ) EnhancedBitMatrix right ) {
        super();
        this.left = left;
        this.right = right;
    }

    @JsonProperty( Names.LEFT_FIELD )
    public EnhancedBitMatrix getLeft() {
        return left;
    }

    @JsonProperty( Names.RIGHT_FIELD )
    public EnhancedBitMatrix getRight() {
        return right;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof QueryHasherPairResult ) ) {
            return false;
        }
        QueryHasherPairResult other = (QueryHasherPairResult) obj;
        if ( left == null ) {
            if ( other.left != null ) {
                return false;
            }
        } else if ( !left.equals( other.left ) ) {
            return false;
        }
        if ( right == null ) {
            if ( other.right != null ) {
                return false;
            }
        } else if ( !right.equals( other.right ) ) {
            return false;
        }
        return true;
    }

}
