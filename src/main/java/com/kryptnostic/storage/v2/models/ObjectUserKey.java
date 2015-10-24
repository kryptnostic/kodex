package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class ObjectUserKey {
    private final UUID    objectId;
    private final UUID    userKey;

    /**
     * This is transient because it is recalculated on the first call to hashcode()
     */
    private transient int cachedHashCode;

    @JsonCreator
    public ObjectUserKey(
            @JsonProperty( Names.ID_FIELD ) UUID objectId,
            @JsonProperty( Names.USER_FIELD ) UUID userKey ) {
        super();
        this.objectId = objectId;
        this.userKey = userKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUserKey() {
        return userKey;
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
            result = prime * result + ( ( userKey == null ) ? 0 : userKey.hashCode() );
            code = result;
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
        ObjectUserKey other = (ObjectUserKey) obj;
        if ( objectId == null ) {
            if ( other.objectId != null ) return false;
        } else if ( !objectId.equals( other.objectId ) ) return false;
        if ( userKey == null ) {
            if ( other.userKey != null ) return false;
        } else if ( !userKey.equals( other.userKey ) ) return false;
        return true;
    }
}
