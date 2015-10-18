package com.kryptnostic.storage.v2.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.kryptnostic.kodex.v1.constants.Names;

public class ObjectUserKey {
    public static final String SEPARATOR = ":";
    private final UUID          objectId;
    private final UUID          userKey;

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
        String[] split = value.split( ObjectUserKey.SEPARATOR );
        Preconditions.checkState( split.length == 0 , "Separator character " + SEPARATOR
                + " should be present for ObjectUserKey" );
        UUID userKey = UUID.fromString( split[0] );
        UUID objectId = UUID.fromString( split[1] );
        return new ObjectUserKey( objectId, userKey );
    }

    public byte[] asBytes() {
        return this.toString().getBytes();
    }

}
