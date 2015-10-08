package com.kryptnostic.directory.v1.model.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.kodex.v1.constants.Names;

public class VisibilityPolicyUpdateRequest {
    private final UUID    domainId;
    private final Boolean publiclyListable;

    @JsonCreator
    public VisibilityPolicyUpdateRequest(
            @JsonProperty( Names.DOMAIN_FIELD ) UUID domainId,
            @JsonProperty( Names.PUBLIC ) boolean publiclyListable ) {
        this.domainId = domainId;
        this.publiclyListable = publiclyListable;
    }

    @JsonProperty( Names.DOMAIN_FIELD )
    public UUID getDomainId() {
        return domainId;
    }

    @JsonProperty( Names.PUBLIC )
    public Boolean getPubliclyListable() {
        return publiclyListable;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( domainId == null ) ? 0 : domainId.hashCode() );
        result = prime * result + ( ( publiclyListable == null ) ? 0 : publiclyListable.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        VisibilityPolicyUpdateRequest other = (VisibilityPolicyUpdateRequest) obj;
        if ( domainId == null ) {
            if ( other.domainId != null ) return false;
        } else if ( !domainId.equals( other.domainId ) ) return false;
        if ( publiclyListable == null ) {
            if ( other.publiclyListable != null ) return false;
        } else if ( !publiclyListable.equals( other.publiclyListable ) ) return false;
        return true;
    }
}
