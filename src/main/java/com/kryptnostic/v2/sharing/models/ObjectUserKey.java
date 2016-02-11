package com.kryptnostic.v2.sharing.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectUserKey implements Comparable<ObjectUserKey> {
    private final UUID         objectId;
    private final UUID         userId;

    @JsonCreator
    public ObjectUserKey(
            @JsonProperty( Names.ID_FIELD ) UUID objectId,
            @JsonProperty( Names.USER_FIELD ) UUID userKey) {
        super();
        this.objectId = objectId;
        this.userId = userKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UUID getUserId() {
        return userId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
        result = prime * result + ( ( userId == null ) ? 0 : userId.hashCode() );
        return result;
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
        if ( userId == null ) {
            if ( other.userId != null ) return false;
        } else if ( !userId.equals( other.userId ) ) return false;
        return true;
    }

    @Override
    public int compareTo( ObjectUserKey o ) {
        int userIdCompare = userId.compareTo( o.userId );
        if ( userIdCompare == 0 ) {
            return objectId.compareTo( o.objectId );
        }
        return userIdCompare;
    }

}
