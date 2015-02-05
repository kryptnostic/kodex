package com.kryptnostic.sharing.v1.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kryptnostic.kodex.v1.constants.Names;

public class DocumentId implements Serializable, Comparable<DocumentId> {
    private static final long serialVersionUID = -273580841535768172L;
    private final String      documentId;

    @JsonCreator
    public DocumentId( @JsonProperty( Names.ID_FIELD ) String documentId ) {
        this.documentId = documentId;
    }

    @JsonProperty( Names.ID_FIELD )
    public String getDocumentId() {
        return documentId;
    }

    public static DocumentId fromId( String id ) {
        return new DocumentId( id );
    }

    public byte[] asBytes() throws JsonProcessingException {
        return documentId.getBytes();
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
        if ( !( obj instanceof DocumentId ) ) {
            return false;
        }
        DocumentId other = (DocumentId) obj;
        if ( documentId == null ) {
            if ( other.documentId != null ) {
                return false;
            }
        } else if ( !documentId.equals( other.documentId ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return documentId;
    }

    @Override
    public int compareTo( DocumentId o ) {
        return documentId.compareTo( o.documentId );
    }
}
