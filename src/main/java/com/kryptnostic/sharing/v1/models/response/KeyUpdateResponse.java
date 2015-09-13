package com.kryptnostic.sharing.v1.models.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public final class KeyUpdateResponse  {
    private final Set<String> ids;

    public KeyUpdateResponse( @JsonProperty( Names.ID_FIELD ) Set<String> ids ) {
        this.ids = ids;
    }

    @JsonProperty( Names.ID_FIELD )
    public Set<String> getIds() {
        return ids;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( ids == null ) ? 0 : ids.hashCode() );
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
        if ( !( obj instanceof KeyUpdateResponse ) ) {
            return false;
        }
        KeyUpdateResponse other = (KeyUpdateResponse) obj;
        if ( ids == null ) {
            if ( other.ids != null ) {
                return false;
            }
        } else if ( !ids.equals( other.ids ) ) {
            return false;
        }
        return true;
    }
    
    
}
