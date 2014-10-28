package com.kryptnostic.storage.v1.models.request;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.kryptnostic.crypto.v1.keys.JacksonKodexMarshaller;
import com.kryptnostic.multivariate.gf2.SimplePolynomialFunction;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class" )
public class QueryHasherPairRequest {
    private static final JacksonKodexMarshaller<SimplePolynomialFunction> marshaller = new JacksonKodexMarshaller<SimplePolynomialFunction>( SimplePolynomialFunction.class );
    public static final String FIELD_LEFT  = "left";
    public static final String FIELD_RIGHT = "right";

    private final byte[]       left;
    private final byte[]       right;

    public QueryHasherPairRequest( SimplePolynomialFunction left, SimplePolynomialFunction right ) throws IOException {
        this.left = marshaller.toBytes( left );
        this.right = marshaller.toBytes( right );
    }

    @JsonCreator
    public QueryHasherPairRequest( @JsonProperty( FIELD_LEFT ) byte[] left, @JsonProperty( FIELD_RIGHT ) byte[] right ) {
        this.left = left;
        this.right = right;
    }

    @JsonProperty( FIELD_LEFT )
    public byte[] getLH() {
        return left;
    }

    @JsonProperty( FIELD_RIGHT )
    public byte[] getRH() {
        return right;
    }

    @JsonIgnore
    public SimplePolynomialFunction getLeft() throws IOException {
        return marshaller.fromBytes( left );
    }
    
    @JsonIgnore
    public SimplePolynomialFunction getRight() throws IOException {
        return marshaller.fromBytes( right );
    }

    public String computeChecksum() {
        return computeChecksum( Hashing.murmur3_128() );
    }
    
    public String computeChecksum( HashFunction hf ) {
        return hf.hashBytes( left ).toString() + hf.hashBytes( right ).toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( left );
        result = prime * result + Arrays.hashCode( right );
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
        if ( !Arrays.equals( left, other.left ) ) {
            return false;
        }
        if ( !Arrays.equals( right, other.right ) ) {
            return false;
        }
        return true;
    }

}
