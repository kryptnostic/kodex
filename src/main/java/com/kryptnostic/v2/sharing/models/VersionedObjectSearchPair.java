package com.kryptnostic.v2.sharing.models;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.indexing.v1.ObjectSearchPair;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

public class VersionedObjectSearchPair {
    private final VersionedObjectKey objectKey;
    private final byte[]             objectSearchPair;

    public VersionedObjectSearchPair( VersionedObjectKey objectKey, ObjectSearchPair objectSearchPair ) {
        this( objectKey, objectSearchPair.getSearchPair() );
    }

    @JsonCreator
    public VersionedObjectSearchPair(
            @JsonProperty( Names.OBJECT_KEY ) VersionedObjectKey objectKey,
            @JsonProperty( Names.OBJECT_SEARCH_PAIR ) byte[] objectSearchPair) {
        Preconditions.checkState( objectSearchPair.length == 2080, "Search pair must be 2080 bytes long." );
        this.objectKey = Preconditions.checkNotNull( objectKey, "ObjectKey cannot be null" );
        this.objectSearchPair = objectSearchPair;
    }

    @JsonProperty( Names.OBJECT_KEY )
    public VersionedObjectKey getObjectKey() {
        return objectKey;
    }

    @JsonProperty( Names.OBJECT_SEARCH_PAIR )
    public byte[] getObjectSearchPair() {
        return objectSearchPair;
    }

    @Override
    public String toString() {
        return "VersionedObjectSearchPair [objectKey=" + objectKey + ", objectSearchPair="
                + Arrays.toString( objectSearchPair ) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectKey == null ) ? 0 : objectKey.hashCode() );
        result = prime * result + Arrays.hashCode( objectSearchPair );
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
        if ( !( obj instanceof VersionedObjectSearchPair ) ) {
            return false;
        }
        VersionedObjectSearchPair other = (VersionedObjectSearchPair) obj;
        if ( objectKey == null ) {
            if ( other.objectKey != null ) {
                return false;
            }
        } else if ( !objectKey.equals( other.objectKey ) ) {
            return false;
        }
        if ( !Arrays.equals( objectSearchPair, other.objectSearchPair ) ) {
            return false;
        }
        return true;
    }

}
