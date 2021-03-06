package com.kryptnostic.v2.indexing.metadata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.kryptnostic.v2.constants.Names;
import com.kryptnostic.v2.storage.models.VersionedObjectKey;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@kryptnostic.com&gt;
 *
 */
public class Metadata {
    private final VersionedObjectKey  objectKey;
    private final String              term;
    private final List<Integer> locations;

    @JsonCreator
    public Metadata(
            @JsonProperty( Names.KEY_FIELD ) VersionedObjectKey objectId,
            @JsonProperty( Names.TOKEN_FIELD ) String term,
            @JsonProperty( Names.INDEX_FIELD ) List<Integer> locations) {
        this.objectKey = objectId;
        this.term = term;
        this.locations = ImmutableList.copyOf( locations );
    }

    @JsonProperty( Names.KEY_FIELD )
    public VersionedObjectKey getObjectKey() {
        return objectKey;
    }

    @JsonProperty( Names.TERM_FIELD )
    public String getTerm() {
        return term;
    }

//    @JsonProperty( Names.LENGTH_FIELD )
//    public int getLength() {
//        return length;
//    }

    @JsonProperty( Names.INDEX_FIELD )
    public List<Integer> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "Metadata [objectId=" + objectKey + ", term=" + term + ", locations=" + locations + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( locations == null ) ? 0 : locations.hashCode() );
        result = prime * result + ( ( objectKey == null ) ? 0 : objectKey.hashCode() );
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
        if ( objectKey == null ) {
            if ( other.objectKey != null ) {
                return false;
            }
        } else if ( !objectKey.equals( other.objectKey ) ) {
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
