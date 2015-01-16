package com.kryptnostic.directory.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class DocumentUserKey implements Serializable {

    private final String  documentId;
    private final UserKey userKey;

    @JsonCreator
    public DocumentUserKey(
            @JsonProperty( Names.ID_FIELD ) String documentId,
            @JsonProperty( Names.USER_FIELD ) UserKey userKey ) {
        super();
        this.documentId = documentId;
        this.userKey = userKey;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getDocumentId() {
        return documentId;
    }

    @JsonProperty( Names.USER_FIELD )
    public UserKey getUserKey() {
        return userKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( documentId == null ) ? 0 : documentId.hashCode() );
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
        if ( !( obj instanceof DocumentUserKey ) ) {
            return false;
        }
        DocumentUserKey other = (DocumentUserKey) obj;
        if ( documentId == null ) {
            if ( other.documentId != null ) {
                return false;
            }
        }
        if ( userKey == null ) {
            if ( other.userKey != null ) {
                return false;
            }
        }
        if ( !documentId.equals( other.documentId ) ) {
            return false;
        }
        if ( !userKey.equals( other.userKey ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return userKey + documentId;
    }

    public byte[] asBytes() {
        return this.toString().getBytes();
    }

}
