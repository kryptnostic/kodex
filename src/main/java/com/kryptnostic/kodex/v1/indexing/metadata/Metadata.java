package com.kryptnostic.kodex.v1.indexing.metadata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kryptnostic.kodex.v1.constants.Names;

public class Metadata {
    private final String        objectId;
    private final String        token;
    private final List<Integer> locations;

    @JsonCreator
    public Metadata(
            @JsonProperty( Names.ID_FIELD ) String objectId,
            @JsonProperty( Names.TOKEN_PROPERTY ) String token,
            @JsonProperty( Names.INDEX_FIELD ) List<Integer> locations ) {
        this.objectId = objectId;
        this.token = token;

        // strip negative locations
        List<Integer> validLocations = Lists.newArrayList();
        for ( Integer i : locations ) {
            if ( i > -1 ) {
                validLocations.add( i );
            }
        }

        this.locations = ImmutableList.copyOf( validLocations );
    }

    @JsonProperty( Names.ID_FIELD )
    public String getObjectId() {
        return objectId;
    }

    @JsonProperty( Names.TOKEN_PROPERTY )
    public String getToken() {
        return token;
    }

    @JsonProperty( Names.INDEX_FIELD )
    public List<Integer> getLocations() {
        return locations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( objectId == null ) ? 0 : objectId.hashCode() );
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
        if ( objectId == null ) {
            if ( other.objectId != null ) {
                return false;
            }
        } else if ( !objectId.equals( other.objectId ) ) {
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
        return "Metadata [objectId=" + objectId + ", token=" + token + ", locations=" + locations + "]";
    }
}
