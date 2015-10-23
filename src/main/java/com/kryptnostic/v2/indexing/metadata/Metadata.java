package com.kryptnostic.v2.indexing.metadata;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.kryptnostic.v2.constants.Names;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class Metadata {
    private final UUID          objectId;
    private final String        term;
    private final int           length;
    private final List<Integer> locations;

    @JsonCreator
    public Metadata(
            @JsonProperty( Names.ID_FIELD ) UUID objectId,
            @JsonProperty( Names.TOKEN_PROPERTY ) String term,
            @JsonProperty( Names.LENGTH_FIELD ) int length,
            @JsonProperty( Names.INDEX_FIELD ) List<Integer> locations ) {
        this.objectId = objectId;
        this.term = term;
        this.length = length;
        this.locations = ImmutableList.copyOf( locations );
    }

    @JsonProperty( Names.ID_FIELD )
    public UUID getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.TERM_FIELD )
    public String getTerm() {
        return term;
    }

    @JsonProperty( Names.LENGTH_FIELD )
    public int getLength() {
        return length;
    }

    @JsonProperty( Names.INDEX_FIELD )
    public List<Integer> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "Metadata [objectId=" + objectId + ", term=" + term + ", locations=" + locations + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( locations == null ) ? 0 : locations.hashCode() );
        result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
        result = prime * result + ( ( term == null ) ? 0 : term.hashCode() );
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
        if ( locations == null ) {
            if ( other.locations != null ) {
                return false;
            }
        } else if ( !locations.equals( other.locations ) ) {
            return false;
        }
        if ( objectId == null ) {
            if ( other.objectId != null ) {
                return false;
            }
        } else if ( !objectId.equals( other.objectId ) ) {
            return false;
        }
        if ( term == null ) {
            if ( other.term != null ) {
                return false;
            }
        } else if ( !term.equals( other.term ) ) {
            return false;
        }
        return true;
    }

}
