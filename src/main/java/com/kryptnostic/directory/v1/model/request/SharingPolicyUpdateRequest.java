package com.kryptnostic.directory.v1.model.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.directory.v1.domain.DomainSharingPolicy;
import com.kryptnostic.kodex.v1.constants.Names;

public class SharingPolicyUpdateRequest {
    private final UUID                domainId;
    private final DomainSharingPolicy sharingPolicy;

    @JsonCreator
    public SharingPolicyUpdateRequest(
            @JsonProperty( Names.DOMAIN_FIELD ) UUID domainId,
            @JsonProperty( Names.SHARING_POLICY ) DomainSharingPolicy sharingPolicy ) {
        this.domainId = domainId;
        this.sharingPolicy = sharingPolicy;
    }

    @JsonProperty( Names.DOMAIN_FIELD )
    public UUID getDomainId() {
        return domainId;
    }

    @JsonProperty( Names.SHARING_POLICY )
    public DomainSharingPolicy getSharingPolicy() {
        return sharingPolicy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( domainId == null ) ? 0 : domainId.hashCode() );
        result = prime * result + ( ( sharingPolicy == null ) ? 0 : sharingPolicy.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        SharingPolicyUpdateRequest other = (SharingPolicyUpdateRequest) obj;
        if ( domainId == null ) {
            if ( other.domainId != null ) return false;
        } else if ( !domainId.equals( other.domainId ) ) return false;
        if ( sharingPolicy != other.sharingPolicy ) return false;
        return true;
    }

}
