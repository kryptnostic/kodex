package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import javax.annotation.concurrent.Immutable;

@Immutable
public class VersionedObjectKey {

    private final UUID    objectId;
    private final long    version;

    /**
     * This is transient because it is recalculated on the first call to hashcode()
     */
    private transient int cachedHashCode;

    public VersionedObjectKey( UUID objectId, long version ) {
        this.objectId = objectId;
        this.version = version;
    }

    /**
     * @return the objectId
     */
    public UUID getObjectId() {
        return objectId;
    }

    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /* (non-Javadoc)
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

    /* (non-Javadoc)
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
}
