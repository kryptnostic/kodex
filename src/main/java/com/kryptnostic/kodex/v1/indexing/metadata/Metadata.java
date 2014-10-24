package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.kryptnostic.sharing.v1.DocumentId;

public class Metadata {
    public static final String  DOCUMENT_ID = "documentId";
    public static final String  TOKEN       = "token";
    public static final String  LOCATIONS   = "locations";

    private final DocumentId    documentId;
    private final String        token;
    private final List<Integer> locations;

    @JsonCreator
    public Metadata(
            @JsonProperty( DOCUMENT_ID ) DocumentId documentId,
            @JsonProperty( TOKEN ) String token,
            @JsonProperty( LOCATIONS ) Iterable<Integer> locations ) {
        this.documentId = documentId;
        this.token = token;
        this.locations = ImmutableList.copyOf( locations );
    }

    @JsonProperty( DOCUMENT_ID )
    public DocumentId getDocumentId() {
        return documentId;
    }

    @JsonProperty( TOKEN )
    public String getToken() {
        return token;
    }

    @JsonProperty( LOCATIONS )
    public List<Integer> getLocations() {
        return locations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( documentId == null ) ? 0 : documentId.hashCode() );
        result = prime * result + ( ( locations == null ) ? 0 : locations.hashCode() );
        result = prime * result + ( ( token == null ) ? 0 : token.hashCode() );
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
        if ( !( obj instanceof Metadata ) ) {
            return false;
        }
        Metadata other = (Metadata) obj;
        if ( documentId == null ) {
            if ( other.documentId != null ) {
                return false;
            }
        } else if ( !documentId.equals( other.documentId ) ) {
            return false;
        }
        if ( locations == null ) {
            if ( other.locations != null ) {
                return false;
            }
        } else if ( !locations.equals( other.locations ) ) {
            return false;
        }
        if ( token == null ) {
            if ( other.token != null ) {
                return false;
            }
        } else if ( !token.equals( other.token ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Metadata [documentId=" + documentId + ", token=" + token + ", locations=" + locations + "]";
    }

}
