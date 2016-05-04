package com.kryptnostic.v2.search;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class IndexSegmentAddress {
    private final byte[] address;

    @JsonCreator
    public IndexSegmentAddress( @JsonProperty( Names.ADDRESS_FIELD ) byte[] address ) {
        this.address = address;
    }

    @JsonProperty( Names.ADDRESS_FIELD )
    public byte[] getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( address );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        IndexSegmentAddress other = (IndexSegmentAddress) obj;
        if ( !Arrays.equals( address, other.address ) ) return false;
        return true;
    }

    @Override
    public String toString() {
        return "IndexSegmentAddress [address=" + Arrays.toString( address ) + "]";
    }

}
