package com.kryptnostic.directory.v1.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.directory.v1.principal.UserKey;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectUserKey implements Serializable {

    public static final String SEPARATOR = ":";
    private final String       objectId;
    private final UserKey      userKey;

    @JsonCreator
    public ObjectUserKey(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.USER_FIELD ) UserKey userKey ) {
        super();
        this.objectId = objectId;
        this.userKey = userKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UserKey getUserKey() {
        return userKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
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
        if ( !( obj instanceof ObjectUserKey ) ) {
            return false;
        }
        ObjectUserKey other = (ObjectUserKey) obj;
        if ( objectId == null ) {
            if ( other.objectId != null ) {
                return false;
            }
        }
        if ( userKey == null ) {
            if ( other.userKey != null ) {
                return false;
            }
        }
        if ( !objectId.equals( other.objectId ) ) {
            return false;
        }
        if ( !userKey.equals( other.userKey ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return userKey + SEPARATOR + objectId;
    }

    public static ObjectUserKey fromString( String value ) {
        int index = value.lastIndexOf( ObjectUserKey.SEPARATOR );
        Preconditions.checkState( index > -1, "Separator character " + SEPARATOR
                + " should be present for ObjectUserKey" );
        String userKeyString = value.substring( 0, index );
        String objectIdString = value.substring( index + 1 );
        UserKey userKey = UserKey.fromString( userKeyString );
        return new ObjectUserKey( objectIdString, userKey );
    }

    public byte[] asBytes() {
        return this.toString().getBytes();
    }

}
