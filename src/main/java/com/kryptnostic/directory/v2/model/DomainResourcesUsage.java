package com.kryptnostic.directory.v2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kryptnostic.v2.constants.Names;

public class DomainResourcesUsage {
    private long available;
    private long total;

    @JsonCreator
    public DomainResourcesUsage(
            @JsonProperty( Names.DOMAIN_AVAILABLE_RESOURCE ) Long available,
            @JsonProperty( Names.DOMAIN_TOTAL_RESOURCE ) Long total) {
        this.available = available;
        this.total = total;
    }

    @JsonProperty( Names.DOMAIN_AVAILABLE_RESOURCE )
    public Long getAvailable() {
        return available;
    }

    @JsonProperty( Names.DOMAIN_TOTAL_RESOURCE )
    public Long getTotal() {
        return total;
    }

    @JsonIgnore
    public void setAvailable( Long available ) {
        this.available = available;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) ( available ^ ( available >>> 32 ) );
        result = prime * result + (int) ( total ^ ( total >>> 32 ) );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( !( obj instanceof DomainResourcesUsage ) ) return false;
        DomainResourcesUsage other = (DomainResourcesUsage) obj;
        if ( available != other.available ) return false;
        if ( total != other.total ) return false;
        return true;
    }   

}
