package com.kryptnostic.storage.v1.models.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class" )
public class QueryHasherPairRequest {

    public static final String             FIELD_LEFT  = "left";
    public static final String             FIELD_RIGHT = "right";

    private final SimplePolynomialFunction left;
    private final SimplePolynomialFunction right;

    @JsonCreator
    public QueryHasherPairRequest(
            @JsonProperty( FIELD_LEFT ) SimplePolynomialFunction left,
            @JsonProperty( FIELD_RIGHT ) SimplePolynomialFunction right ) {
        this.left = left;
        this.right = right;
    }

    @JsonProperty( FIELD_LEFT )
    public SimplePolynomialFunction getLeft() {
        return left;
    }

    @JsonProperty( FIELD_RIGHT )
    public SimplePolynomialFunction getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( left == null ) ? 0 : left.hashCode() );
        result = prime * result + ( ( right == null ) ? 0 : right.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof QueryHasherPairRequest ) ) {
            return false;
        }
        QueryHasherPairRequest other = (QueryHasherPairRequest) obj;
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
