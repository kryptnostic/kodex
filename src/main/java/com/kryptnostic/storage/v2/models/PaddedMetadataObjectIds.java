package com.kryptnostic.storage.v2.models;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class PaddedMetadataObjectIds {
    private final byte[]     key;
    private final List<UUID> objectIds;

    public PaddedMetadataObjectIds(
            @JsonProperty( Names.KEY_FIELD ) byte[] key,
            @JsonProperty( Names.OBJECT_IDS ) List<UUID> objectIds ) {
        this.key = key;
        this.objectIds = objectIds;
    }

    public byte[] getKey() {
        return key;
    }

    public List<UUID> getObjectIds() {
        return objectIds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( key );
        result = prime * result + ( ( objectIds == null ) ? 0 : objectIds.hashCode() );
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
        if ( !( obj instanceof PaddedMetadataObjectIds ) ) {
            return false;
        }
        PaddedMetadataObjectIds other = (PaddedMetadataObjectIds) obj;
        if ( !Arrays.equals( key, other.key ) ) {
            return false;
        }
        if ( objectIds == null ) {
            if ( other.objectIds != null ) {
                return false;
            }
        } else if ( !objectIds.equals( other.objectIds ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PaddedMetadataIdsMapping [key=" + Arrays.toString( key ) + ", metadataObjectIds=" + objectIds
                + "]";
    }

}
