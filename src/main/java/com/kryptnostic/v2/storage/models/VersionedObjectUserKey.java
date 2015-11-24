package com.kryptnostic.v2.storage.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.sharing.models.ObjectUserKey;

public class VersionedObjectUserKey {

    private final UUID    objectId;
    private final UUID    userId;
    private final long    version;

    /**
     * This is transient because it is recalculated on the first call to hashcode()
     */
    private transient int cachedHashCode;

    @JsonCreator
    public VersionedObjectUserKey(
            @JsonProperty( Names.ID_FIELD ) UUID objectId,
            @JsonProperty( Names.USER_FIELD ) UUID userId,
            @JsonProperty( Names.OBJECT_VERSION_FIELD ) long version) {
        super();
        this.objectId = objectId;
        this.userId = userId;
        this.version = version;
    }

    public static VersionedObjectUserKey fromVersionedObjectKey( VersionedObjectKey objectKey, UUID userId ) {
        return new VersionedObjectUserKey( objectKey.getObjectId(), userId, objectKey.getVersion() );
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUserKey() {
        return userId;
    }

    @JsonProperty( Names.OBJECT_VERSION_FIELD )
    public long getVersion() {
        return version;
    }

    @JsonIgnore
    public VersionedObjectKey getVersionedObjectKey() {
        return new VersionedObjectKey( objectId, version );
    }

    @JsonIgnore
    public ObjectUserKey getObjectUserKey() {
        return new ObjectUserKey( objectId, userId );
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int code = cachedHashCode;
        if ( cachedHashCode <= 0 ) {
            final int prime = 31;
            int result = 1;
            result = prime * cachedHashCode + ( ( objectId == null ) ? 0 : objectId.hashCode() );
            result = prime * cachedHashCode + ( ( userId == null ) ? 0 : userId.hashCode() );
            result = prime * cachedHashCode + (int) ( version ^ ( version >>> 32 ) );
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
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        VersionedObjectUserKey other = (VersionedObjectUserKey) obj;
        if ( objectId == null ) {
            if ( other.objectId != null ) {
                return false;
            }
        } else if ( !objectId.equals( other.objectId ) ) {
            return false;
        }
        if ( userId == null ) {
            if ( other.userId != null ) {
                return false;
            }
        } else if ( !userId.equals( other.userId ) ) {
            return false;
        }
        if ( version != other.version ) {
            return false;
        }
        return true;
    }

}
