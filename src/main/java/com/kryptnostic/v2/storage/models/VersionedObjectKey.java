package com.kryptnostic.v2.storage.models;

import java.util.UUID;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

@Immutable
public class VersionedObjectKey {

    private final UUID    objectId;
    private final long    version;

    /**
     * This is transient because it is recalculated on the first call to hashcode()
     */
    private transient int cachedHashCode;

    @JsonCreator
    public VersionedObjectKey(
            @JsonProperty( Names.OBJECT_ID_FIELD ) UUID objectId,
            @JsonProperty( Names.OBJECT_VERSION_FIELD ) long version) {
        this.objectId = objectId;
        this.version = version;
    }

    /**
     * @return the objectId
     */
    @JsonProperty( Names.OBJECT_ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    /**
     * @return the version
     */
    @JsonProperty( Names.OBJECT_VERSION_FIELD )
    public long getVersion() {
        return version;
    }

    public static VersionedObjectKey newRandomKey() {
        return new VersionedObjectKey( UUID.randomUUID(), 0L );
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int code = cachedHashCode;
        if ( code == 0 ) {
            final int prime = 31;
            int result = 1;
            result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
            result = prime * result + (int) ( version ^ ( version >>> 32 ) );
            cachedHashCode = code = result;
        }
        return code;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        VersionedObjectKey other = (VersionedObjectKey) obj;
        if ( objectId == null ) {
            if ( other.objectId != null ) return false;
        } else if ( !objectId.equals( other.objectId ) ) return false;
        if ( version != other.version ) return false;
        return true;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append( "{" )
                .append( objectId ).append( "/" ).append( version )
                .append( "}" )
                .toString();
    }

    public static VersionedObjectKey fromObjectMetadata( ObjectMetadata metadata ) {
        return new VersionedObjectKey( metadata.getId(), metadata.getVersion() );
    }
}
